package com.ericg.kripto.data.mapper

import com.ericg.kripto.data.local.entity.CoinEntity
import com.ericg.kripto.data.local.entity.ExchangeEntity
import com.ericg.kripto.data.remote.dto.*
import com.ericg.kripto.domain.model.*

internal fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        isNew = isNew,
        isActive = isActive,
        type = type
    )
}

internal fun CoinEntity.toCoin(): Coin {
    return Coin(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        isNew = isNew,
        isActive = isActive,
        type = type
    )
}

internal fun CoinDto.toCoinEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        isNew = isNew,
        isActive = isActive,
        type = type
    )
}

internal fun CoinDetailsDto.toCoinDetails(): CoinDetails {
    return CoinDetails(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        isNew = isNew,
        isActive = isActive,
        type = type,
        tags = tagDtos?.map { it.toTag() },
        team = team?.map { it.toMember() },
        description = description,
        message = message,
        openSource = openSource,
        startedAt = startedAt,
        developmentStatus = developmentStatus,
        links = links?.toCoinDetailLink(),
        linksExtended = linksExtendedDto?.map { it.toLinksExtended() },
    )
}

internal fun ExchangeDto.toExchange(): Exchange {
    return Exchange(
        active = active,
        adjustedRank = adjustedRank,
        apiStatus = apiStatus,
        confidenceScore = confidenceScore,
        currencies = currencies,
        description = description,
        fiats = fiatDtos?.map { it.toFiat() },
        id = id,
        lastUpdated = lastUpdated,
        links = linksDto?.toLinks(),
        markets = markets,
        marketsDataFetched = marketsDataFetched,
        message = message,
        name = name,
        quotes = quotesDto?.toQuotes(),
        reportedRank = reportedRank,
        sessionsPerMonth = sessionsPerMonth?.toInt(),
        websiteStatus = websiteStatus
    )
}

internal fun ExchangeDto.toExchangeEntity(): ExchangeEntity {
    return ExchangeEntity(
        id = id ?: "",
        name = name ?: "",
        description = description ?: "",
        active = active ?: false,
        adjustedRank = adjustedRank ?: 0,
        reportedRank = reportedRank ?: 0,
        markets = markets ?: 0,
        currencies = currencies ?: 0,
        twitterLinks = linksDto?.twitter ?: emptyList(),
        websiteLinks = linksDto?.website ?: emptyList()
    )
}

internal fun ExchangeEntity.toExchange(): Exchange {
    return Exchange(
        id = id,
        name = name,
        description = description,
        active = active,
        adjustedRank = adjustedRank,
        reportedRank = reportedRank,
        markets = markets,
        currencies = currencies,
        links = Links(twitterLinks, websiteLinks),
        apiStatus = null,
        confidenceScore = null,
        fiats = null,
        lastUpdated = null,
        marketsDataFetched = null,
        message = null,
        quotes = null,
        sessionsPerMonth = null,
        websiteStatus = null
    )
}

internal fun PriceConversionDto.toPriceConversion(): PriceConversion {
    return PriceConversion(
        baseCurrencyId = baseCurrencyId,
        baseCurrencyName = baseCurrencyName,
        basePriceLastUpdated = basePriceLastUpdated,
        quoteCurrencyId = quoteCurrencyId,
        quoteCurrencyName = quoteCurrencyName,
        quotePriceLastUpdated = quotePriceLastUpdated,
        amount = amount,
        price = price
    )
}

private fun FiatDto.toFiat(): Fiat {
    return Fiat(
        name = name,
        symbol = symbol
    )
}

private fun LinksDto.toLinks(): Links {
    return Links(
        twitter = twitter,
        website = website
    )
}

private fun QuotesDto.toQuotes(): Quotes {
    return Quotes(
        uSD = usdDto.toUsd()
    )
}

private fun USDDto.toUsd(): USD {
    return USD(
        reportedVolume24h = reportedVolume24h,
        adjustedVolume24h = adjustedVolume24h,
        reportedVolume7d = reportedVolume7d,
        adjustedVolume7d = adjustedVolume7d,
        reportedVolume30d = reportedVolume30d,
        adjustedVolume30d = adjustedVolume30d
    )
}

private fun TagDto.toTag(): Tag {
    return Tag(
        id = id,
        name = name,
        coinCounter = coinCounter,
        icoCounter = icoCounter
    )
}

private fun TeamMemberDto.toMember(): TeamMember {
    return TeamMember(
        id = id,
        name = name,
        position = position
    )
}

private fun CoinDetailLinkDto.toCoinDetailLink(): CoinDetailLink {
    return CoinDetailLink(
        explorer = explorer,
        facebook = facebook,
        reddit = reddit,
        sourceCode = sourceCode,
        website = website,
        youtube = youtube
    )
}

private fun StatsDto?.toStats(): Stats {
    return Stats(
        subscribers = this?.subscribers,
        contributors = this?.contributors,
        stars = this?.stars,
        followers = this?.followers
    )
}

private fun LinksExtendedDto.toLinksExtended(): LinksExtended {
    return LinksExtended(url = url, type = type, stats = statsDto.toStats())
}

private fun WhitePaperDto.toWhitePaper(): WhitePaper {
    return WhitePaper(
        link = link,
        thumbnail = thumbnail
    )
}
