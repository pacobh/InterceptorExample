package es.paco.interceptorexample.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import es.paco.interceptorexample.R
import es.paco.interceptorexample.databinding.ActivityMainBinding
import es.paco.interceptorexample.extension.TAG
import es.paco.interceptorexample.ui.base.BaseActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun inflateBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() = Unit

    override fun observeViewModel() {
        mainViewModel.loadingMutableLiveData.observe(this, this::showLoading)
        mainViewModel.errorMutableLiveData.observe(this, this::showErrorInformativeOnly)

        lifecycleScope.launch {
            mainViewModel.channelError.collect() {
                Log.d(TAG, "l> mainViewModel.channelError.collect(): $it")
                showErrorInformativeOnly(it)
            }
        }

        lifecycleScope.launch {
            mainViewModel.stateName.collect() {
                Log.d(TAG, "l> mainViewModel.stateName.collect(): $it")
                binding.tvPokemonName.text = it
            }
        }

        lifecycleScope.launch {
            mainViewModel.stateImage.collect() {
                Log.d(TAG, "l> mainViewModel.stateImage.collect(): $it")
                Glide.with(this@MainActivity)
                    .load(it)
                    .placeholder(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(binding.ivPokemonImage)
            }
        }
    }

    override fun createAfterInflateBindingSetupObserverViewModel(savedInstanceState: Bundle?) {
        binding.btRefreshPokemon.setOnClickListener { mainViewModel.emitRandomName() }
        mainViewModel.loadPokemonList()
    }

    override fun configureToolbar() = Unit

    override fun needRefreshTokenInViewModel() = Unit

}