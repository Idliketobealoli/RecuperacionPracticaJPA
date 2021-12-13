package model

import javax.persistence.*

@Entity
@Table(name = "Issue")
@NamedQuery(name = "Issue.findAll", query = "SELECT r FROM model.Issue r")
class Issue() {
    @Id
    @Column(name = "id", nullable = false)
    lateinit var id: String
    @OneToOne
    lateinit var author: Programmer
    @Basic
    @Column(name = "title", nullable = false, length = 255)
    lateinit var title: String
    @Basic
    @Column(name = "text", nullable = true)
    var text: String? = null
    @Basic
    @Column(name = "date", nullable = true, length = 10)
    lateinit var date: String
    @ManyToMany(mappedBy = "Issue", cascade = arrayOf(CascadeType.DETACH))
    var programmers: List<Programmer>? = null
    @ManyToOne
    @JoinColumn(name = "project_id")
    lateinit var project: Project
    @ManyToOne
    @JoinColumn(name = "repository_id")
    lateinit var repository: Repository
    @Basic
    @Column(name = "isFinished", nullable = false)
    var isFinished: Boolean = false

    constructor(
        id: String,
        author: Programmer,
        title: String,
        text: String?,
        date: String,
        programmers: List<Programmer>?,
        project: Project,
        repository: Repository,
        isFinished: Boolean = false
    ) : this() {
        this.id = id
        this.author = author
        this.title = title
        this.text = text
        this.date = date
        this.programmers = programmers
        this.project = project
        this.repository = repository
        this.isFinished = isFinished
    }

    override fun toString(): String {
        return ("Issue{ id:$id, author:${author.id}, title:$title, " +
                "text:$text, date:$date, programmers:${programmers?.forEach { "${it.id};" } ?: ""}, " +
                "project:${project.id}, repository:${repository.id}, isFinished:$isFinished }")
    }
}