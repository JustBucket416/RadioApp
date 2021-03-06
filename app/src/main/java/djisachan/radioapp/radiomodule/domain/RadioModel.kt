package djisachan.radioapp.radiomodule.domain

/**
 * Модель радиостанции. Пока одна на 3 слоя
 * @author Markova Ekaterina on 25-Jul-20
 */
data class RadioModel(
    val stationuuid: String,
    val name: String,
    val url: String?,
    val imageUrl: String
)