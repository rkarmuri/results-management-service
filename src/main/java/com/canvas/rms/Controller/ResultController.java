package com.canvas.rms.Controller;

import com.canvas.rms.DTO.ResultDTO;
import com.canvas.rms.Entity.Result;
import com.canvas.rms.Service.ResultService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PostMapping
    public ResponseEntity<Result> createResult(@RequestBody Result result) {
        Result savedResult = resultService.saveResult(result);
        return ResponseEntity.ok(savedResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getResultById(@PathVariable("id") int resultId) {
        Result result = resultService.getResultById(resultId);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable("id") int resultId) {
        resultService.deleteResult(resultId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/course/{courseId}")
    public ResponseEntity<?> addOrUpdateResultsForCourse(@PathVariable int courseId, @RequestBody List<Result> results) {
        resultService.addOrUpdateResultsForCourse(courseId, results);
        return ResponseEntity.status(HttpStatus.OK).body("Results updated successfully");
    }

    @GetMapping("/course/{courseId}/student/{studentId}")
    public ResponseEntity<List<ResultDTO>> getResultsByCourseIdAndStudentId(@PathVariable int courseId, @PathVariable int studentId) {
        List<ResultDTO> results = resultService.getResultsByCourseIdAndStudentId(courseId, studentId);
        return ResponseEntity.ok(results);
    }

}
