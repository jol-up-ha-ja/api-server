package com.balancemania.api.user.infrastructure

import com.balancemania.api.user.domain.QUser
import com.balancemania.api.user.domain.User
import com.balancemania.api.user.domain.vo.OauthInfo
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UserRepository : JpaRepository<User, Long>, UserCustomRepository {
    @Transactional(readOnly = true)
    fun existsByOauthInfo(oauthInfo: OauthInfo): Boolean

    @Transactional(readOnly = true)
    fun findByOauthInfo(oauthInfo: OauthInfo): User?
}

interface UserCustomRepository

class UserCustomRepositoryImpl : UserCustomRepository, QuerydslRepositorySupport(User::class.java) {
    @Autowired
    @Qualifier("maniaEntityManager")
    override fun setEntityManager(entityManager: EntityManager) {
        super.setEntityManager(entityManager)
    }

    private val qUser = QUser.user
}
