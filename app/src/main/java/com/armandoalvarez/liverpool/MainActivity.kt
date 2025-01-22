package com.armandoalvarez.liverpool

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.armandoalvarez.liverpool.data.model.Record
import com.armandoalvarez.liverpool.data.util.Resource
import com.armandoalvarez.liverpool.data.util.Sorted
import com.armandoalvarez.liverpool.databinding.ActivityMainBinding
import com.armandoalvarez.liverpool.presentation.adapter.ActvAdapter
import com.armandoalvarez.liverpool.presentation.adapter.ProductsAdapter
import com.armandoalvarez.liverpool.presentation.viewmodel.LiverpoolViewModel
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: LiverpoolViewModel by viewModels()

    private val handler = Handler(Looper.getMainLooper())
    private var inputDelay = 1000L
    private var inputRunnable: Runnable? = null


    private var dialogIsDisplayed = false
    private var dialogLoadingIsDisplayed = false
    private var loadingDialog: Dialog? = null

    private var page = 1

    private lateinit var list: List<Sorted>

    @Inject
    lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        configListeners()
        configRecyclerView()
        configActv()
    }

    private fun configActv() {

        list = listOf(
            Sorted(getString(R.string.lower_price_text), 0),
            Sorted(getString(R.string.higher_price_text), 1)
        )

        val actvAdapter = ActvAdapter(this, list)
        binding.actvFilter.setAdapter(actvAdapter)
        binding.actvFilter.setDropDownBackgroundDrawable(
            ColorDrawable(getColor(R.color.backgroundColor))
        )
    }

    private fun configRecyclerView() {

        binding.rvProducts.apply {
            adapter = this@MainActivity.adapter
            layoutManager = GridLayoutManager(
                this@MainActivity,
                1,
                GridLayoutManager.VERTICAL,
                false
            )
        }

    }

    private fun configListeners() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // no action
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                inputRunnable?.let {
                    handler.removeCallbacks(it)
                }

                inputRunnable = Runnable {
                    if (binding.etSearch.text.toString().isNotEmpty()) {
                        page = 1
                        adapter.differ.submitList(mutableListOf())
                        searchProduct()
                        binding.actvFilter.setText("")
                    }
                }

                handler.postDelayed(inputRunnable!!, inputDelay)
            }

            override fun afterTextChanged(s: Editable?) {
                // no action
            }
        })

        binding.actvFilter.setOnItemClickListener { _, _, _, _ ->
            val sorted = list.find { it.name == binding.actvFilter.text.toString() }

            page = 1
            adapter.differ.submitList(mutableListOf())
            searchProductSorted(sorted!!.value)
        }
    }

    private fun searchProduct() {
        viewModel.productsResponse = MutableLiveData()
        viewModel.searchProduct(binding.etSearch.text.toString().trim(), page)
        viewModel.productsResponse.observeOnce(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideLoading()

                    response.data?.let { productsResponse ->

                        if (productsResponse.plpResults?.records != null && productsResponse.plpResults!!.records!!.isNotEmpty()) {
                            val records = adapter.differ.currentList.toMutableList()
                            records.addAll(productsResponse.plpResults!!.records!!)

                            adapter.differ.submitList(records)
                            binding.rvProducts.visibility = View.VISIBLE
                            binding.tilFilter.visibility = View.VISIBLE
                            binding.tvFilter.visibility = View.VISIBLE
                            binding.tvNoResults.visibility = View.GONE

                        } else {
                            binding.rvProducts.visibility = View.GONE
                            binding.tilFilter.visibility = View.GONE
                            binding.tvFilter.visibility = View.GONE
                            binding.tvNoResults.visibility = View.VISIBLE
                        }

                        if ((productsResponse.plpResults?.plpState?.totalNumRecs
                                ?: 0) > (page * (productsResponse.plpResults?.plpState?.recsPerPage
                                ?: 0))
                        ) {
                            binding.btnLoad.visibility = View.VISIBLE
                            binding.btnLoad.setOnClickListener {
                                page++
                                searchProduct()
                            }

                        } else {
                            binding.btnLoad.visibility = View.GONE
                        }


                    }
                }

                is Resource.Error -> {
                    hideLoading()
                    showAlert(response.message ?: "")
                }

                is Resource.Loading -> {
                    showLoading()
                }
            }
        }
    }

    private fun searchProductSorted(sorted: Int) {
        viewModel.productsResponse = MutableLiveData()
        viewModel.searchProductSorted(binding.etSearch.text.toString().trim(), page, sorted)
        viewModel.productsResponse.observeOnce(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideLoading()

                    response.data?.let { productsResponse ->

                        if (productsResponse.plpResults?.records != null && productsResponse.plpResults!!.records!!.isNotEmpty()) {
                            val records = sortList(productsResponse.plpResults?.records!!, sorted)

                            adapter.differ.submitList(records)
                            binding.rvProducts.visibility = View.VISIBLE
                            binding.tilFilter.visibility = View.VISIBLE
                            binding.tvFilter.visibility = View.VISIBLE
                            binding.tvNoResults.visibility = View.GONE

                        } else {
                            binding.rvProducts.visibility = View.GONE
                            binding.tilFilter.visibility = View.GONE
                            binding.tvFilter.visibility = View.GONE
                            binding.tvNoResults.visibility = View.VISIBLE
                        }

                        if ((productsResponse.plpResults?.plpState?.totalNumRecs
                                ?: 0) > (page * (productsResponse.plpResults?.plpState?.recsPerPage
                                ?: 0))
                        ) {
                            binding.btnLoad.visibility = View.VISIBLE
                            binding.btnLoad.setOnClickListener {
                                page++
                                searchProductSorted(sorted)
                            }

                        } else {
                            binding.btnLoad.visibility = View.GONE
                        }


                    }
                }

                is Resource.Error -> {
                    hideLoading()
                    showAlert(response.message ?: "")
                }

                is Resource.Loading -> {
                    showLoading()
                }
            }
        }
    }

    private fun sortList(records: List<Record?>, sorted: Int): List<Record?> {
        val list = mutableListOf<Record?>()

        list.addAll(
            if (sorted == 0) records.sortedBy { it?.listPrice }
            else records.sortedByDescending { it?.listPrice }
        )

        return list
    }


    private fun <T> LiveData<Resource<T>>.observeOnce(
        owner: LifecycleOwner,
        observer: (Resource<T>) -> Unit
    ) {
        observe(owner, object : Observer<Resource<T>> {
            override fun onChanged(value: Resource<T>) {
                when (value) {
                    is Resource.Success -> {
                        value.data?.let {
                            removeObserver(this)
                            observer(value)
                        }
                    }

                    is Resource.Error -> {
                        value.message?.let {
                            removeObserver(this)
                            observer(value)
                        }
                    }

                    is Resource.Loading -> {
                        observer(value)
                    }
                }
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun showLoading() {
        if (!dialogLoadingIsDisplayed) {
            dialogLoadingIsDisplayed = true
            val layout = layoutInflater.inflate(R.layout.dialog_fragment_loading, null)

            loadingDialog = Dialog(this@MainActivity)
            loadingDialog?.let { loadingDialog ->

                loadingDialog.setContentView(layout)
                loadingDialog.setCanceledOnTouchOutside(false)
                loadingDialog.setOnKeyListener { _, keyCode, event ->
                    keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP
                }

                loadingDialog.show()

                loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                loadingDialog.window?.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }


    private fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
        dialogLoadingIsDisplayed = false
    }

    @SuppressLint("InflateParams")
    fun showAlert(
        message: String,
    ) {
        val layout = layoutInflater.inflate(R.layout.dialog_fragment_alert, null)

        val displayRectangle = Rect()
        val window: Window = this.window
        window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        layout.minimumWidth = (displayRectangle.width() * 0.9f).toInt()

        val dialog = Dialog(this)
        dialog.setContentView(layout)
        val btn = layout.findViewById<Button>(R.id.btnAlertAccept)
        btn.setOnClickListener {
            dialogIsDisplayed = false

            dialog.dismiss()
        }

        val text = layout.findViewById<MaterialTextView>(R.id.tvAlertBody)
        text.text = message

        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (!dialogIsDisplayed) {
            dialog.show()
            dialogIsDisplayed = true
        }
    }
}