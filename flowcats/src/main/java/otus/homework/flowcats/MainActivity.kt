package otus.homework.flowcats

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import otus.homework.flowcats.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    private val layoutBinding: ActivityMainBinding by viewBinding(
        ActivityMainBinding::bind
    )

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.catsStateData.collect { state ->
                when (state) {
                    is Result.Loading -> layoutBinding.progressBar.toVisible()
                    is Result.Success -> {
                        view.populate(state.data)
                        layoutBinding.progressBar.toInvisible()
                    }
                    is Result.Error -> {
                        layoutBinding.progressBar.toInvisible()
                    }
                }
            }
        }
    }
}