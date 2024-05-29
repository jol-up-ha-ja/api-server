package com.balancemania.api.balance.infrastructure

import com.balancemania.api.balance.domain.Balance
import com.balancemania.api.balance.domain.QBalance
import com.balancemania.api.extension.executeSlice
import com.querydsl.jpa.impl.JPAQuery
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface BalanceRepository : JpaRepository<Balance, Long>, BalanceCustomRepository {
    @Transactional(readOnly = true)
    fun existsByIdAndUid(uid: Long, balanceId: Long): Boolean
}

interface BalanceCustomRepository{
    @Transactional(readOnly = true)
    fun getBalancesByUid(uid: Long, pageable: Pageable): Slice<Balance>
}

class BalanceCustomRepositoryImpl : BalanceCustomRepository, QuerydslRepositorySupport(Balance::class.java) {
    @Autowired
    @Qualifier("maniaEntityManager")
    override fun setEntityManager(entityManager: EntityManager) {
        super.setEntityManager(entityManager)
    }

    private val qBalance = QBalance.balance

    override fun getBalancesByUid(uid:Long, pageable: Pageable): Slice<Balance> {
        val query = JPAQuery<QBalance>(entityManager)
            .select(qBalance)
            .from(qBalance)
            .where(
                qBalance.uid.eq(uid)
            ).orderBy(
                qBalance.createdAt.desc()
            )

        return querydsl.executeSlice(query, pageable)
    }
}
