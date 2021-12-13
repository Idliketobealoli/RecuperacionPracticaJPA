package model

import javax.persistence.*

@Entity
@Table(name = "Department")
@NamedQuery(name = "Department.findAll", query = "SELECT r FROM model.Department r")
class Department() {
    @Id
    @Column(name = "id", nullable = false)
    lateinit var id: String
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    lateinit var name: String
    @OneToOne
    lateinit var boss: Programmer
    @Basic
    @Column(name = "budget", nullable = false)
    var budget: Double = 0.0
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "Department", cascade = arrayOf(CascadeType.REMOVE))
    var finishedProjects: List<Project>? = null
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "Department", cascade = arrayOf(CascadeType.REMOVE))
    var developingProjects: List<Project>? = null
    @Basic
    @Column(name = "anualBudget", nullable = false)
    var anualBudget: Double = 0.0
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "Department", cascade = arrayOf(CascadeType.REMOVE))
    lateinit var bossHistory: List<Programmer>

    constructor(
        id: String,
        name: String,
        boss: Programmer,
        budget: Double,
        finishedProjects: List<Project>?,
        developingProjects: List<Project>?,
        anualBudget: Double,
        bossHistory: List<Programmer>
    ) : this() {
        this.id = id
        this.name = name
        this.boss = boss
        this.budget = budget
        this.finishedProjects = finishedProjects
        this.developingProjects = developingProjects
        this.anualBudget = anualBudget
        this.bossHistory = bossHistory
    }

    override fun toString(): String {
        return ("Department:{ id:$id, name:$name, boss:${boss.id}, budget:$budget, " +
                "finishedProjects:${finishedProjects?.forEach { "${it.id};" } ?: ""}, " +
                "developingProjects:${developingProjects?.forEach { "${it.id};" } ?: ""}, " +
                "anualBudget:$anualBudget, bossHistory:${bossHistory.forEach { "${it.id};" }} }")
    }
}