package auth.web.controller.request;

public record AddressRequest(
        String road,
        String number,
        String city,
        String state,
        String zipCode
) {
}
