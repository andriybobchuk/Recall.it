import androidx.compose.ui.window.ComposeUIViewController
import com.recallit.app.App
import com.recallit.app.KoinInitializer

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) {
    App()
}