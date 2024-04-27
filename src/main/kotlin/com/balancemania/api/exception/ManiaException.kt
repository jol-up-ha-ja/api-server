package com.balancemania.api.exception

open class ManiaException(
    val errorCode: ErrorCode,
    override val message: String? = errorCode.description,
    val extra: Map<String, Any>? = null,
) : RuntimeException(message ?: errorCode.description)

class NotFoundException(errorCode: ErrorCode) : ManiaException(errorCode)

class InvalidTokenException(errorCode: ErrorCode) : ManiaException(errorCode)

class InvalidRequestException(errorCode: ErrorCode) : ManiaException(errorCode)

class FailToCreateException(errorCode: ErrorCode) : ManiaException(errorCode)

class AlreadyException(errorCode: ErrorCode) : ManiaException(errorCode)

class NoAuthorityException(errorCode: ErrorCode) : ManiaException(errorCode)

class FailToExecuteException(errorCode: ErrorCode) : ManiaException(errorCode)