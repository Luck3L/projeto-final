package br.gov.caixaverso.projeto.resources;

import br.gov.caixaverso.projeto.domainmodel.Course;
import br.gov.caixaverso.projeto.domainmodel.Lesson;
import br.gov.caixaverso.projeto.dto.CourseRequest;
import br.gov.caixaverso.projeto.dto.CourseResponse;
import br.gov.caixaverso.projeto.dto.LessonRequest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
public class CourseResource {

    private static final Logger log = LoggerFactory.getLogger(CourseResource.class);

    @GET
    public Response getCourses() {
        List<Course> courses = Course.listAll();

        List<CourseResponse> courseResponses = courses.stream()
                .map(course -> new CourseResponse(course.getId(), course.getName(), course.getLessons()))
                .toList();

        return Response.ok(courseResponses, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    public Response getCourse(@PathParam("id") Long id) {
        Course course = Course.findById(id);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(course, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Transactional
    public Response createCourse(@Valid CourseRequest courseRequest) {
        Course course = new Course(courseRequest.name(), courseRequest.lesson());
        course.persist();

        return Response.created(URI.create("/courses/" + course.getId()))
//                .header("Content-Type", MediaType.APPLICATION_JSON)
                .entity(new CourseResponse(course.getId(), course.getName(), course.getLessons()))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateCourse(@PathParam("id") Long id, @Valid CourseRequest courseRequest) {
        Course course = Course.findById(id);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        course.setName(courseRequest.name());
        course.persist();

        return Response.ok(new CourseResponse(course.getId(), course.getName(), List.of()),
                MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteCourse(@PathParam("id") Long id) {
        Course course = Course.findById(id);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        course.delete();
        return Response.noContent().build();
    }

    @GET
    @Path("{id}/lessons")
    public Response buscarLessonsPorCourseId(@PathParam("id") Long id) {
        Course course = Course.findById(id);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(course.getLessons(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/{id}/lessons")
    @Transactional
    public Response CadastrarLessonPorCourseId(@PathParam("id") Long id, @Valid LessonRequest lessonRequest) {
        Course curso = Course.findById(id);

        if (curso == null) return Response.status(Response.Status.NOT_FOUND).build();

        Lesson novaLesson = new Lesson(lessonRequest.name());
        novaLesson.persist();
        curso.adicionarLesson(novaLesson);

        return Response.created(URI.create("/courses/%d/lessons/%d".formatted(id, novaLesson.getId())))
//                .header("Content-Type", MediaType.APPLICATION_JSON)
                .entity(novaLesson)
                .build();
    }

}
