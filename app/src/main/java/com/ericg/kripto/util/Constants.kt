package com.ericg.kripto.util

object Constants {
    /** END POINTS
     *
     * [/coins] : List of Coins.
     *
     * [/coins/{coin_id}] : Returns details of a Coin.
     *
     * [/exchanges] : List of exchanges/markets.
     *
     * [/exchanges/{exchange_id}] : Returns basic information about a given exchange on.
     *
     * [/price-converter?base_currency_id={coin_id}&quote_currency_id={coin_id}&amount={value}] : returns the conversion between base and quote coins.
     *
     * See more in the ApiService...
     */
    const val BASE_URL = "https://api.coinpaprika.com/v1/"
}
