import dto.NewsDTO
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TallinnNewsIntegrationTest extends AnyFlatSpec with Matchers
{
	"get simple news" should "ok" in
	{
		val expected = NewsDTO(
			"Informing parents of school appointments based on place of residence began in Tallinn",
			"Young athletes The Tallinn Education Department began informing parents of school appointments based " +
				"on place of residence for children entering Grade 1 on 1 September 2023. Parents can confirm school " +
				"locations until June 10.",
			"May 23, 2023"
		)

		val actual = TallinnNews.getNews(9)

		assert(actual.header == expected.header)
		assert(actual.date == expected.date)

		assert(actual.content == expected.content)
	}

	"get old news" should "ok" in
	{
		val expected = NewsDTO(
			"Construction work in the Ülemiste area has required buses to reroute",
			"As a result of construction work on Valukoja street, bus lines 7, 15, 45, 49, 64 and 65 will have their " +
				"routes and bus stop locations changed from May 22 to June 2. In addition, the route of bus line 52 will " +
				"be changing in the direction of Ülemiste station.",
			"May 14, 2023"
		)

		assert(TallinnNews.getNews(1) == expected)
	}

	"get not existing news" should "throw" in {
		assertThrows[org.jsoup.HttpStatusException](TallinnNews.getNews(0))
	}
}