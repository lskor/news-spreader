
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import dto.NewsDTO

class GreenCity54IntegrationTest extends AnyFlatSpec with Matchers
{
	"get simple news" should "ok" in
	{
		val expected = NewsDTO(
			"26 апреля — будет отключен лифт",
			"Уважаемые жители!" +
				"\n\n \n\n " +
				"26 апреля 2022 г. (во вторник) в доме по ул. Лескова, 25 (подъезды 3 и 4) будет отключен " +
				"пассажирский лифт в связи с проведением работ. " +
				"\n\n \n\n " +
				"Приносим извинения за временные неудобства. " +
				"\n\n",
			"25 апреля 2022"
		)

		val actual = GreenCity54.getNews(1249)

		assert(actual.header == expected.header)
		assert(actual.date == expected.date)

		assert(actual.content == expected.content)
	}

	"get old news" should "ok" in
	{
		val expected = NewsDTO(
			"Просьба погасить задолженность!",
			"Уважаемые жители ЖК «Оазис»! Просим срочно погасить образовавшуюся задолженность по оплате жилищно-коммунальных услуг!" +
				"\n\n " +
				"При наличии задолженности за услуги ЖКХ заявки на выполнение любых работ по квартирам (кроме аварийных) приниматься не будут." +
				"\n\n",
			"7 ноября 2013"
		)

		assert(GreenCity54.getNews(81) == expected)
	}

	"get not existing news" should "throw" in {
		assertThrows[org.jsoup.HttpStatusException](GreenCity54.getNews(0))
	}
}