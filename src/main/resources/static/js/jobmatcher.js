const form = document.querySelectorAll('.offer-logo');

form.forEach((element, index, array) => {
    element.addEventListener('mouseover', e => {
        element.querySelector('.job-offer-title').classList.add('slide-up');
    });

    element.addEventListener('mouseout', e => {
        element.querySelector('.job-offer-title').classList.remove('slide-up');
    });
});

new GLightbox();

const loginForm = document.querySelectorAll('#form-login');

loginForm.forEach((element, index, array) => {
    element.addEventListener('submit', e => {
        if (!element.checkValidity()) {
            e.preventDefault();
            e.stopPropagation();
        }

        element.classList.add('was-validated');
    });
});

const loginFormAdmin = document.querySelectorAll('#form-login-admin');

loginFormAdmin.forEach((element, index, array) => {
    element.addEventListener('submit', e => {
        if (!element.checkValidity()) {
            e.preventDefault();
            e.stopPropagation();
        }

        element.classList.add('was-validated');
    });
});

$(document).ready(function () {
    $("#fave-button").on("mouseover", function () {
        let icon = $("#fave-button-icon");

        if (icon.hasClass("active") || icon.hasClass("deactivated")) {
            return;
        }

        icon.removeClass("bi-suit-heart").addClass("bi-suit-heart-fill");
    });

    $("#fave-button").on("mouseleave", function () {
        let icon = $("#fave-button-icon");

        if (icon.hasClass("active")) {
            return;
        }

        icon.removeClass("bi-suit-heart-fill deactivated").addClass("bi-suit-heart");
    });

    let errorCallback = function(jqXHR, type, error) {
        if (jqXHR.status == 401 || jqXHR.status == 403) {
            window.location.replace("/user/login");
        } else {
            let alertModal = new bootstrap.Modal(document.getElementById("modal-alert"));
            alertModal.show();
        }
    };

    $("#fave-button").on("click", function () {
        let icon = $("#fave-button-icon");
        let csrfValue = $("meta[name='_csrf']").attr("content");
        let csrfHeaderName = $("meta[name='_csrf_header']").attr("content");
        let jobOfferId = $("meta[name='_job_offer_id']").attr("content");

        if (icon.hasClass("active")) {
            $.ajax({
                url: "/user/favourites",
                type: "DELETE",
                data: jQuery.param({ jobOfferId: jobOfferId }) ,
                headers: {
                    [csrfHeaderName]: csrfValue
                },
                beforeSend: function () {
                    $("#fave-button").prop("disabled", true);
                    $('#fave-button-indicator').html("<span id=\"fave-button-spinner\" class=\"spinner-border spinner-border-sm\" aria-hidden=\"true\"></span>");
                },
                complete: function (jqXHR, textStatus) {
                    $("#fave-button").prop("disabled", false);

                    if (textStatus == "nocontent") {
                        $('#fave-button-indicator').html("<i id=\"fave-button-icon\" class=\"bi bi-suit-heart deactivated\"></i>");
                    } else {
                        $('#fave-button-indicator').html("<i id=\"fave-button-icon\" class=\"bi bi-suit-heart-fill active\"></i>");
                    }
                },
                error: errorCallback
            });
        } else {
            $.ajax({
                url: "/user/favourites",
                type: "POST",
                data: jQuery.param({ jobOfferId: jobOfferId }) ,
                headers: {
                    [csrfHeaderName]: csrfValue
                },
                beforeSend: function () {
                    $("#fave-button").prop("disabled", true);
                    $('#fave-button-indicator').html("<span id=\"fave-button-spinner\" class=\"spinner-border spinner-border-sm\" aria-hidden=\"true\"></span>");
                },
                complete: function (jqXHR, textStatus) {
                    $("#fave-button").prop("disabled", false);

                    if (textStatus == "nocontent") {
                        $('#fave-button-indicator').html("<i id=\"fave-button-icon\" class=\"bi bi-suit-heart-fill active\"></i>");
                    } else {
                        $('#fave-button-indicator').html("<i id=\"fave-button-icon\" class=\"bi bi-suit-heart\"></i>");
                    }
                },
                error: errorCallback
            });
        }
    });
});