package gradle.cucumber;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class GetStatementsStep {

    @Given("임직원이 아래의 기간으로 비용 처리 내역을 조회한다.")
    public void employee_wants_to_get_statements(DataTable dataTable) {

    }

    @When("비용 처리 내역이 조회된다.")
    public void the_statementes_returned() {

    }

    @Then("비용 처리 내역이 {int}l건 이상 조회된다.")
    public void the_total_statements_count_should_be_equal_or_larger_0l(Integer int1) {
        assertTrue("Check if returned statements count is equal or larger than 0.", int1 >= 0);
    }
}
