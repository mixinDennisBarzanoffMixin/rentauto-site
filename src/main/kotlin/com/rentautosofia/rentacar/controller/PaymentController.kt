package com.rentautosofia.rentacar.controller

import org.springframework.ui.Model
import javax.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import com.paypal.base.rest.PayPalRESTException
import com.rentautosofia.rentacar.config.PaypalPaymentIntent
import com.rentautosofia.rentacar.config.PaypalPaymentMethod
import com.rentautosofia.rentacar.repository.BookingRepository
import com.rentautosofia.rentacar.service.PaypalService
import com.rentautosofia.rentacar.util.URLUtils
import com.rentautosofia.rentacar.util.findOne
import org.springframework.util.MultiValueMap
import com.rentautosofia.rentacar.repository.CarRepository
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@Controller
class PaymentController(@Autowired
                        private val paypalService: PaypalService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var bookingRepository: BookingRepository
    @Autowired
    private lateinit var carRepository: CarRepository

    @PostMapping("/pay")
    fun pay(model: Model, request: HttpServletRequest, @RequestBody multiParams: MultiValueMap<String, String>): String {
        val params = multiParams.toSingleValueMap()
//        val deposit: Int
        //deposit = //if (params["deposit"] != null) {
            //params["deposit"]!!.toInt()
        //} else {
        val booking = bookingRepository.findOne(params["orderId"]!!.toInt())!!
        val car = carRepository.findOne(booking.carId)!!
//        deposit = car.getPricePerDayFor(booking.startDate daysTill booking.endDate)
        //}
        val cancelUrl = URLUtils.getBaseURl(request) + PAYPAL_CANCEL_URL
        val successUrl = URLUtils.getBaseURl(request) + PAYPAL_SUCCESS_URL

        val amount = when (params["payment"]) {
            "earnest" -> 50
            "all" -> booking.totalPrice
            else -> throw IllegalArgumentException("Unrecognized option: ${params["payment"]}")
        }
    
        logger.info("DEPOSIT about to be paid for: { amount: ???$amount }, booking: $booking")

        try {
            val payment = paypalService.createPayment(
//                    deposit.toDouble(),
                    amount.toFloat(),
                    "EUR",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "RentAuto Sofia deposit payment",
                    cancelUrl,
                    "$successUrl?orderId=${booking.id}&amount=$amount")
            for (links in payment.links) {
                if (links.rel == "approval_url") {
                    return "redirect:" + links.href
                }
            }
        } catch (e: PayPalRESTException) {
            logger.error(e.message)
        }

        return "redirect:/"
    }

    @GetMapping(PAYPAL_CANCEL_URL)
    fun cancelPay(model: Model): String {
        model.addAttribute("view", "cancel")
        return "client-base-layout"
    }

    @GetMapping(PAYPAL_SUCCESS_URL)
    fun successPay(@RequestParam orderId: Int, @RequestParam amount: Int, model: Model, @RequestParam("paymentId") paymentId: String, @RequestParam("PayerID") payerId: String): String {
        try {
            val payment = paypalService.executePayment(paymentId, payerId)
            if (payment.state == "approved") {

                logger.info("DEPOSIT PAYED for id: $orderId")
                val booking = bookingRepository.findOne(orderId)!!
                booking.earnest += amount
                bookingRepository.saveAndFlush(booking) // Make it paid

                model.addAttribute("view", "success")
                return "client-base-layout"
            }
        } catch (e: PayPalRESTException) {
            logger.error(e.message)
        }

        return "redirect:/"
    }

    companion object {
        const val PAYPAL_SUCCESS_URL = "/pay/success"
        const val PAYPAL_CANCEL_URL = "/pay/cancel"
    }

}
