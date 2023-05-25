import dto.NewsDTO

object NewsFormatter {
	def get(dto: NewsDTO): String = s"*${dto.header}*\n\n${dto.content}\n\n_${dto.date}_"
}