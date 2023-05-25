import dto.NewsDTO
import org.jsoup.Jsoup

object TallinnNews
{
	private val url = "https://lskor.github.io/"

	def getNews(point: Long): NewsDTO =
	{
		val doc = Jsoup.connect(s"$url$point/").get()

		val header = doc.select(".post-title").first().text()
		val content = doc.select(".post-content > p").first().text()
		val date = doc.select(".dt-published").text()

		NewsDTO(header, content, date)
	}
}