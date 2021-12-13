package model

import javax.persistence.*

@Entity
@Table(name = "Repository")
@NamedQuery(name = "Repository.findAll", query = "SELECT r FROM model.Repository r")
class Repository() {
    @Id
    @Column(name = "id", nullable = false)
    lateinit var id: String
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    lateinit var name: String
    @Basic
    @Column(name = "creationDate", nullable = false, length = 10)
    lateinit var creationDate: String
    @OneToOne
    lateinit var project: Project
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "Repository", cascade = arrayOf(CascadeType.REMOVE))
    var commits: List<Commit>? = null
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "Repository", cascade = arrayOf(CascadeType.REMOVE))
    var issues: List<Issue>? = null

    constructor(
        id: String,
        name: String,
        creationDate: String,
        project: Project,
        commits: List<Commit>?,
        issues: List<Issue>?
    ) : this() {
        this.id = id
        this.name = name
        this.creationDate = creationDate
        this.project = project
        this.commits = commits
        this.issues = issues
    }

    override fun toString(): String {
        val string = ("Repository{ id:$id, name:$name, creationDate:$creationDate, project:${project.id}, " +
                "commits:${commits?.forEach { "${it.id};" } ?: ""}, issues:${issues?.forEach { "${it.id};" } ?: ""}}")
        return string
    }
}