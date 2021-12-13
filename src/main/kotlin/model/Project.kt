package model

import Technology
import javax.persistence.*

@Entity
@Table(name = "Project")
@NamedQuery(name = "Project.findAll", query = "SELECT x FROM model.Project x")
class Project() {
    @Id
    @Column(name = "id", nullable = false)
    lateinit var id: String
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    lateinit var department: Department
    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    lateinit var projectManager: Programmer
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    lateinit var name: String
    @Basic
    @Column(name = "budget", nullable = false)
    var budget: Double = 0.0
    @Basic
    @Column(name = "startDate", nullable = false, length = 10)
    lateinit var startDate: String
    @Basic
    @Column(name = "endDate", nullable = true, length = 10)
    var endDate: String? = null
    @Basic
    @Column(name = "technologies", nullable = true)
    var technologies: List<Technology>? = null
    @OneToOne
    @PrimaryKeyJoinColumn
    lateinit var repository: Repository
    @Basic
    @Column(name = "isFinished", nullable = false)
    var isFinished: Boolean = false
    @ManyToMany(mappedBy = "Project", cascade = arrayOf(CascadeType.DETACH))
    var programmers: List<Programmer>? = null

    constructor(
        id: String,
        department: Department,
        projectManager: Programmer,
        name: String,
        budget: Double = 0.0,
        startDate: String,
        endDate: String? = null,
        technologies: List<Technology>? = null,
        repository: Repository,
        isFinished: Boolean = false,
        programmers: List<Programmer>? = null
    ) : this() {
        this.id = id
        this.department = department
        this.projectManager = projectManager
        this.name = name
        this.budget = budget
        this.startDate = startDate
        this.endDate = endDate
        this.technologies = technologies
        this.repository = repository
        this.isFinished = isFinished
        this.programmers = programmers
    }

    override fun toString(): String {
        return ("Project{ id:$id, department:${department.id}, projectManager:${projectManager.id}, " +
                "name:$name, budget:$budget, startDate:$startDate, endDate:$endDate, " +
                "technologies:${technologies?.forEach { "${it.toString()};" } ?: ""}, " +
                "repository:${repository.id}, isFinished:$isFinished, " +
                "programmers:${programmers?.forEach { "${it.id};" } ?: ""}}")
    }
}