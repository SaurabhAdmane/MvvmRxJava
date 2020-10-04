package com.saurabh.synerzipdemo.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

/**
 * Created by SaurabhA on 04,October,2020
 *
 */
@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class WeatherResponseBean(
    @field:JsonProperty("coord")
    var coord: Coord? = null,
    @field:JsonProperty("weather")
    var weather: List<Weather>? = null,
    @field:JsonProperty("base")
    var base: String? = null,
    @field:JsonProperty("main")
    var main: Main? = null,
    @field:JsonProperty("visibility")
    var visibility: Int? = null,
    @field:JsonProperty("wind")
    var wind: Wind? = null,
    @field:JsonProperty("clouds")
    var clouds: Clouds? = null,
    @field:JsonProperty("dt")
    var dt: Int? = null,
    @field:JsonProperty("sys")
    var sys: Sys? = null,
    @field:JsonProperty("timezone")
    var timezone: Int? = null,
    @field:JsonProperty("id")
    var id: Int? = null,
    @field:JsonProperty("name")
    var name: String? = null,
    @field:JsonProperty("cod")
    var cod: Int? = null
) : Parcelable

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Main(
    @field:JsonProperty("temp")
    var temp: Double,
    @field:JsonProperty("feels_like")
    var feelsLike: Double,
    @field:JsonProperty("temp_min")
    var tempMin: Double,
    @field:JsonProperty("temp_max")
    var tempMax: Double,
    @field:JsonProperty("pressure")
    var pressure: Int,
    @field:JsonProperty("humidity")
    var humidity: Int
) : Parcelable {
    constructor() : this(0.0, 0.0, 0.0, 0.0, 0, 0)
}

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Coord(
    @field:JsonProperty("lon")
    var lon: Double,
    @field:JsonProperty("lat")
    var lat: Double
) : Parcelable {
    constructor() : this(0.0, 0.0)
}

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Clouds(
    @field:JsonProperty("all")
    var all: Int
) : Parcelable {
    constructor() : this(0)
}

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Wind(
    @field:JsonProperty("speed")
    var speed: Int,
    @field:JsonProperty("deg")
    var deg: Int
) : Parcelable {
    constructor() : this(0, 0)
}

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Weather(
    @field:JsonProperty("id")
    var id: Int,
    @field:JsonProperty("main")
    var main: String,
    @field:JsonProperty("description")
    var description: String,
    @field:JsonProperty("icon")
    var icon: String
) : Parcelable {
    constructor() : this(0,"","", "")
}

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Sys(
    @field:JsonProperty("type")
    var type: Int,
    @field:JsonProperty("id")
    var id: Int,
    @field:JsonProperty("country")
    var country: String,
    @field:JsonProperty("sunrise")
    var sunrise: Int,
    @field:JsonProperty("sunset")
    var sunset: Int
) : Parcelable {
    constructor() : this(0, 0, "", 0, 0)
}