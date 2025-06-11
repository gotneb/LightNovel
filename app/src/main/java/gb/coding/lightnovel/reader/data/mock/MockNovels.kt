package gb.coding.lightnovel.reader.data.mock

import gb.coding.lightnovel.reader.domain.models.Novel

object MockNovels {
    val sample = Novel(
        id = "1",
        title = "Renegade Immortal",
        author = "Wang Er",
        status = "Ativo",
        coverImage = "https://us-east-1.linodeobjects.com/novelmania/uploads/novel/cover/63/capa_ir.jpg",
        description = "Wang Lin é um garoto muito esperto com parentes amáveis. Embora ele e seus pais sejam evitados pelo resto dos seus parentes, seus pais sempre mantiveram muita esperança de que ele algum dia se tornará alguém grandioso. Um dia, Wang Lin repentinamente ganhou uma chance de trilhar o caminho de um imortal, mas descobriu que ele apenas tinha um talento medíocre no máximo. Acompanhe Wang Lin enquanto ele supera sua falta de talento e trilha o caminho em direção a tornar-se um verdadeiro imortal!",
        tags = listOf("Ação", "Adulto", "Aventura", "Artes Marciais", "Fantasia", "Romance", "Sobrenatural", "Xianxia"),
    )
}