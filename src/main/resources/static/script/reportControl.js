$(document).ready(function() {
    // Select all divs inside #page, hide them
    $("#page div").hide();

    // Show the elements that need to be visible initially
    $("#reportIntro, #btnBox, #startBtn, #previousConsumptionChart, #prosumerProductionChart, #predictExpenditureBar, #predictExpenditurePie").show();

    // Hide all input elements inside #btnBox
    $("#btnBox input").hide();

    // Show #startBtn
    $("#startBtn").show();

    // Define an array of divs to be shown in order
    var divs = ["#intro1", "#chart1", "#intro2", "#chart2", "#intro3", "#chart3"];
    var currentDiv = 0;

    // When #startBtn is clicked
    $("#startBtn").click(function() {
        $("#reportIntro").hide();
        $(this).hide(); // Hide #startBtn
        $(divs[currentDiv]).show(); // Show the first div in the array
        $("#prevBtn, #nextBtn").show(); // Show #prevBtn and #nextBtn
        $("#prevBtn").prop("disabled", true); // Disable #prevBtn
    });

    // When #nextBtn is clicked
    $("#nextBtn").click(function() {
        $(divs[currentDiv]).hide(); // Hide the current div
        currentDiv++; // Move to the next div
        $(divs[currentDiv]).show(); // Show the next div
        $("#prevBtn").prop("disabled", false); // Enable #prevBtn
        if (currentDiv == divs.length - 1) { // If the last div is reached
            $(this).prop("disabled", true); // Disable #nextBtn
            $("#endBtn").show(); // Show #endBtn
        }
    });

    // When #prevBtn is clicked
    $("#prevBtn").click(function() {
        $(divs[currentDiv]).hide(); // Hide the current div
        currentDiv--; // Move to the previous div
        $(divs[currentDiv]).show(); // Show the previous div
        $("#nextBtn").prop("disabled", false); // Enable #nextBtn
        if (currentDiv == 0) { // If the first div is reached
            $(this).prop("disabled", true); // Disable #prevBtn
        }
    });
});