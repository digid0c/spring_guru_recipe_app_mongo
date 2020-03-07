package guru.samples.recipe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

//    @ExceptionHandler(NumberFormatException.class)
//    @ResponseStatus(BAD_REQUEST)
//    public ModelAndView handleNumberFormatException(Exception exception) {
//        log.error(exception.getMessage(), exception);
//
//        ModelAndView modelAndView = new ModelAndView("error-400");
//        modelAndView.addObject("exception", exception);
//        return modelAndView;
//    }
}
