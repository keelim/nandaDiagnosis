/*
 * Designed and developed by 2020 keelim (Jaehyun Kim)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.keelim.nandadiagnosis.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.keelim.nandadiagnosis.R
import com.keelim.nandadiagnosis.databinding.FragmentInappwebBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebFragment : Fragment() {
  private var _binding: FragmentInappwebBinding? = null
  private val binding get() = _binding!!
  private val args by navArgs<WebFragmentArgs>()

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val contextWrapper = ContextThemeWrapper(requireActivity(), R.style.InAppWebview)
    val cloneInflater = inflater.cloneInContext(contextWrapper)
    _binding = FragmentInappwebBinding.inflate(cloneInflater, container, false)

    binding.webview.apply {
      webViewClient = WebViewClient()
      webChromeClient = WebChromeClient()
      settings.javaScriptEnabled = true
      loadUrl(args.url)
    }

    initEvent()
    return binding.root
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)

    requireActivity().onBackPressedDispatcher.addCallback(
      this,
      object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          if (binding.webview.canGoBack()) {
            binding.webview.goBack()
          } else {
            findNavController().navigate(R.id.navigation_category)
          }
        }
      }
    )
  }

  private fun initEvent() {
    binding.addressBar.setOnEditorActionListener { view, actionId, event ->
      if (actionId == EditorInfo.IME_ACTION_DONE) {
        val loadingUrl = view.text.toString()
        if (URLUtil.isNetworkUrl(loadingUrl)) {
          binding.webview.loadUrl(loadingUrl)
        } else {
          binding.webview.loadUrl("accepted server data")
        }
      }
      return@setOnEditorActionListener false
    }

    binding.backButton.setOnClickListener {
      binding.webview.goBack()
    }

    binding.forwardButton.setOnClickListener {
      binding.webview.goForward()
    }

    binding.homeButton.setOnClickListener {
      binding.webview.loadUrl(args.url)
    }

    binding.refreshLayout.setOnRefreshListener {
      binding.webview.reload()
    }
  }

  inner class WebViewClient : android.webkit.WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
      super.onPageStarted(view, url, favicon)
      binding.progressbar.show()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
      super.onPageFinished(view, url)
      binding.refreshLayout.isRefreshing = false
      binding.progressbar.hide()
      binding.backButton.isEnabled = binding.webview.canGoBack()
      binding.forwardButton.isEnabled = binding.webview.canGoForward()
//      binding.addressBar.setText(url)
    }
  }

  inner class WebChromeClient : android.webkit.WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
      super.onProgressChanged(view, newProgress)
      binding.progressbar.progress = newProgress
    }
  }
}