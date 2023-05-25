
import scala.concurrent.duration._
import canoe.models.Channel
object Config {
	val token: String = "5824728765:AAGLiLgHDd718X_JAlH2o7lQ8oQ-gTYNBWI"
	val url = "http://localhost:9001/getPost"
	val duration = 5.second
	val tallinnNews = Channel(id = -1001950191075L, title = Some("Tallinn News"), username = Some("tallinn_newsss"))
}
