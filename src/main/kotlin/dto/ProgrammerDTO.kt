package dto

import Technology
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import model.Commit
import model.Department
import model.Issue
import model.Project
import javax.xml.bind.annotation.*

/**
 * Data transfer Object de Programmer. Preparado para ser sacado en formato XML y JSON.
 * @author Jaime Salcedo
 */
@XmlRootElement(name = "programmer")
@XmlAccessorType(XmlAccessType.FIELD)
class ProgrammerDTO() {
    @Expose
    @XmlAttribute
    lateinit var id: String
    @Expose
    lateinit var name: String

    @Expose
    @XmlAttribute(name = "register_date")
    lateinit var registerDate: String
    @Expose
    lateinit var department: Department

    @XmlElementWrapper(name = "active_projects")
    @Expose
    var activeProjects: List<Project>? = null

    @XmlElementWrapper
    @Expose
    var commits: List<Commit>? = null

    @XmlElementWrapper
    @Expose
    var issues: List<Issue>? = null

    @XmlElementWrapper
    @Expose
    var technologies: List<Technology>? = null
    @Expose
    var salary: Double = 0.0

    @XmlAttribute(name = "department_boss")
    @Expose
    var isDepBoss: Boolean = false

    @XmlAttribute(name = "project_manager")
    @Expose
    var isProjectManager: Boolean = false

    @XmlAttribute(name = "active")
    @Expose
    var isActive: Boolean = false

    @XmlTransient
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
            password:String
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

    /**
     * De un string JSON lo convierte en un ProgrammerDTO
     * @author Jaime Salcedo
     * @param json String
     * @return ProgrammerDTO
     */
    fun fromJSON(json: String): ProgrammerDTO? {
        return Gson().fromJson(json, ProgrammerDTO::class.java)
    }

    /**
     * De un ProgrammerDTO lo convierte en un string JSON
     * @author Jaime Salcedo
     * @return String
     */
    fun toJSON(): String {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create()
        return gson.toJson(this)
    }
}