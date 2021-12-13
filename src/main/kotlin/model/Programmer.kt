package model

import Technology
import org.hibernate.annotations.ColumnTransformer
import javax.persistence.*

@Entity
@Table(name = "Programmer")
@NamedQueries(
    NamedQuery(name = "Programmer.findAll", query = "SELECT p FROM model.Programmer p"),
    //NamedQuery(name = "Programmer.getFromDepartmentOrderCommit", query = "SELECT p FROM model.Programmer WHERE p.department.id = ? ORDER BY p.commits.size DESC")
)
class Programmer() {
    @Id
    @Column(name = "id", nullable = false)
    lateinit var id: String
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    lateinit var name: String
    @Basic
    @Column(name = "registerDate", nullable = false, length = 10)
    lateinit var registerDate: String
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    lateinit var department: Department
    @ManyToMany(cascade = arrayOf(CascadeType.DETACH))
    @JoinTable(name = "Project_Programmer", joinColumns = arrayOf(JoinColumn(name = "id_programmer")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "id_project"))
    )
    var activeProjects: List<Project>? = null
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "Programmer", cascade = arrayOf(CascadeType.REMOVE))
    var commits: List<Commit>? = null
    @ManyToMany(cascade = arrayOf(CascadeType.DETACH))
    @JoinTable(name = "Programmer_Issue", joinColumns = arrayOf(JoinColumn(name = "id_programmer")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "id_issue"))
    )
    var issues: List<Issue>? = null
    @Basic
    @Column(name = "technologies", nullable = true)
    var technologies: List<Technology>? = null
    @Basic
    @Column(name = "salary", nullable = false)
    var salary: Double = 0.0
    @Basic
    @Column(name = "isDepBoss", nullable = false)
    var isDepBoss: Boolean = false
    @Basic
    @Column(name = "isProjectManager", nullable = false)
    var isProjectManager: Boolean = false
    @Basic
    @Column(name = "isActive", nullable = false)
    var isActive: Boolean = false
    @Basic
    @ColumnTransformer(write = " SHA(?) ")
    @Column(name = "password", nullable = false)
    lateinit var password: String

    constructor(
        id: String,
        name: String,
        registerDate: String,
        department: Department,
        activeProjects: List<Project>?,
        commits: List<Commit>?,
        issues: List<Issue>?,
        technologies: List<Technology>?,
        salary: Double = 0.0,
        isDepBoss: Boolean = false,
        isProjectManager: Boolean = false,
        isActive: Boolean = false,
        password: String
    ) : this() {
        this.id = id
        this.name = name
        this.registerDate = registerDate
        this.department = department
        this.activeProjects = activeProjects
        this.commits = commits
        this.issues = issues
        this.technologies = technologies
        this.salary = salary
        this.isDepBoss = isDepBoss
        this.isProjectManager = isProjectManager
        this.isActive = isActive
        this.password = password
    }

    override fun toString(): String {
        return ("Programmer{ id:$id, name:$name, registerDate:$registerDate, department:${department.id}, " +
                "activeProjects:${activeProjects?.forEach { "${it.id};" } ?: ""}, " +
                "commits:${commits?.forEach { "${it.id};" } ?: ""}, " +
                "issues:${issues?.forEach { "${it.id};" } ?: ""}, technologies:${technologies?.forEach { "${it.toString()};" } ?: ""}, " +
                "salary:$salary, isDepartmentBoss:$isDepBoss, isProjectManager:$isProjectManager, isActive:$isActive}")
    }
}