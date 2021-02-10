package com.gunt.imagefinder.data.repository

interface DomainMapper<Dto, DomainModel> {

    fun mapToDomainModel(model: Dto): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): Dto
}
