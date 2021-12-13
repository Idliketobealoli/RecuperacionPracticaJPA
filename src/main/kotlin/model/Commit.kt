package model

import javax.persistence.*
import javax.xml.bind.annotation.XmlAttribute

@Entity
@Table(name = "Commite")
@NamedQuery(name = "Commite.findAll", query = "SELECT r FROM model.Commit r")
class Commit() {
    @Id
    @Column(name = "id", nullable = false)
    lateinit var id: String
    @Basic
    @Column(name = "title", nullable = false, length = 255)
    lateinit var title: String
    @Basic
    @Column(name = "text", nullable = true)
    var text: String? = null
    @Basic
    @Column(name = "date", nullable = false, length = 10)
    lateinit var date: String
    @ManyToOne
    @JoinColumn(name = "repository_id", nullable = false)
    lateinit var repository: Repository
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    lateinit var project: Project
    @ManyToOne
    @JoinColumn(name = "programmer_id", nullable = false)
    lateinit var author: Programmer
    @OneToOne
    lateinit var issue: Issue

    constructor(
        id: String,
        title: String,
        text: String? = null,
        date: String,
        repository: Repository,
        project: Project,
        author: Programmer,
        issue: Issue
    ) : this() {
        this.id = id
        this.title = title
        this.text = text
        this.date = date
        this.repository = repository
        this.project = project
        this.author = author
        this.issue = issue
    }

    override fun toString(): String {
        return ("Commit:{ id:$id, title:$title, text:$text, date:$date, " +
                "repository:${repository.id}, project:${project.id}, author:${author.id}, " +
                "issue:${issue.id} }")
    }
}