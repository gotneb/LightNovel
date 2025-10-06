package gb.coding.lightnovel.reader.data.mock

import gb.coding.lightnovel.core.domain.model.LanguageCode
import gb.coding.lightnovel.reader.domain.models.Novel

object MockNovels {
    val sample = Novel(
        id = "1",
        language = LanguageCode.GERMAN,
        title = "Renegade Immortal",
        author = "Wang Er",
        status = "Ativo",
        coverImage = "https://us-east-1.linodeobjects.com/novelmania/uploads/novel/cover/63/capa_ir.jpg",
        description = "Wang Lin é um garoto muito esperto com parentes amáveis. Embora ele e seus pais sejam evitados pelo resto dos seus parentes, seus pais sempre mantiveram muita esperança de que ele algum dia se tornará alguém grandioso. Um dia, Wang Lin repentinamente ganhou uma chance de trilhar o caminho de um imortal, mas descobriu que ele apenas tinha um talento medíocre no máximo. Acompanhe Wang Lin enquanto ele supera sua falta de talento e trilha o caminho em direção a tornar-se um verdadeiro imortal!",
    )

    val sample2 = Novel(
        id = "2",
        language = LanguageCode.BRAZILIAN_PORTUGUESE,
        title = "Alya às Vezes Esconde seus Sentimentos em Russo",
        author = "SunSunSun",
        status = "Ativo",
        coverImage = "https://us-east-1.linodeobjects.com/novelmania/uploads/novel/cover/290/alya_v5_capa_1.jpg",
        description = "Masachika Kuze senta-se ao lado de Alya Mikhailovna Kujou, uma garota de ascendência russa e japonesa. Ela é bonita, inteligente e uma aluna excepcional em diversas áreas, enquanto Masachika é um nerd e conhecido por ser preguiçoso. Ele é um alvo fácil para seus, aparentemente, ríspidos comentários em russo, que a deixa muito feliz por ninguém conseguir traduzir… exceto por Masachika que consegue entender o que ela realmente está dizendo!"
    )

    val sample3 = Novel(
        id = "3",
        language = LanguageCode.BRAZILIAN_PORTUGUESE,
        title = "Soberbo Deus da Alquimia",
        author = "Ji Xiao Zei",
        status = "Ativo",
        coverImage = "https://us-east-1.linodeobjects.com/novelmania/uploads/novel/cover/83/capa_sda.jpg",
        description = "Chen Xiang tinha sido golpeado com infortúnio, ele nasceu sem uma veia espiritual, então ele não podia praticar artes marciais. No entanto foi sua sorte que ele teve um encontro fatídico com algumas beldades misteriosas. Isso fez sua vida dar uma volta para melhor no caminho do cultivo e alquimia. Esse mundo novo e excitante é cheio de imortais, demônios e deuses e, bestas místicas celestiais. Enquanto ele começa sua aventura ao longo da vida, ele cruza com muitos segredos e mistérios escondidos no seu mundo. Explore com nosso herói como ele contemplar esses tentadores e profundos mistérios e atingir o pico do caminho marcial, enquanto ele flerta com mulheres, faz amizades, desafia lordes, imortais e demônios do mundo marcial."
    )

    val samples = listOf(sample, sample2, sample3)
}