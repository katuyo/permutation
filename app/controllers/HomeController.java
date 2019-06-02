package controllers;

import com.google.common.collect.Lists;
import play.mvc.Controller;
import play.mvc.Result;
import services.PermutationService;

import javax.inject.Inject;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    private PermutationService permutationService;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result permutate(Integer n) {
        List<String> result = Lists.newArrayList("Not support for n >= 10, for now n==" + n);
        if (n < 10) {
            result = permutationService.permuatate(n);
        }
        return ok(views.html.permutation.render("Permutation", result));
    }
}
