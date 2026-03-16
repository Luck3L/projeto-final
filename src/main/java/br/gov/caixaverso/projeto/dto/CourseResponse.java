package br.gov.caixaverso.projeto.dto;

import br.gov.caixaverso.projeto.domainmodel.Lesson;

import java.util.List;

public record CourseResponse(
        Long id,
        String name,
        List<Lesson> lessons
) {
}
