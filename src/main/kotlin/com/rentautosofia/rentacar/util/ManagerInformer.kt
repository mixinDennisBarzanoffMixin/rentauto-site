package com.rentautosofia.rentacar.util

import com.rentautosofia.rentacar.entity.BookingRequest
import com.rentautosofia.rentacar.repository.CarRepository
import com.rentautosofia.rentacar.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper


@Component
class ManagerInformer {
    @Autowired
    lateinit var emailSender: JavaMailSender
    @Autowired
    lateinit var carRepository: CarRepository
    @Autowired
    lateinit var customerRepository: CustomerRepository

    fun informManagerWith(BookingRequest: BookingRequest) {
        val car = carRepository.findOne(BookingRequest.carId)!!
        val customer = customerRepository.findOne(BookingRequest.customerId)!!
        val mimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, false, "UTF-8")


        val html =
                """
                <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
            "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>Untitled Document</title>
    </head>

    <body style="margin:0">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" widtd="100%">
        <tr valign="top">
            <td height="30" align="center" bgcolor="#eeeeee">&nbsp;</td>
        </tr>
        <tr valign="top">
            <td align="center" bgcolor="#eeeeee">
                <table width="600" border="0" align="center" cellpadding="0" cellspacing="0" widtd="600">
                    <tr>
                        <td bgcolor="#FFFFFF">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="center" bgcolor="#FFFFFF">
                            <table width="500" border="0" align="center" cellpadding="0" cellspacing="0"
                                   style="font-family:Arial, Helvetica, sans-serif;" widtd="500">
                                <tr>
                                    <td align="center" bgcolor="#333333" style="padding:15px 0"><h1
                                            style="color:#fff; font-size:24px; font-weight:bold; margin:0;">
                                            ???????? ???????????? ???? ????????</h1></td>
                                </tr>
                                <tr>
                                    <td align="left"><p
                                            style="font-size:14px; color:#555; font-weight:normal; line-height:20px;">
                                        <strong>?????????? ????????????????????????????,</strong><br>
                                        ?????????? ?? ???????????????? ???????????? ???? ???????? ???? ????????, ?????? ???? ??????????????????.</p></td>
                                </tr>
                                <tr>
                                    <td height="20"></td>
                                </tr>
                                <tr>
                                    <td>
                                        <table width="500" border="0" cellspacing="0" cellpadding="5"
                                               style="font-size:14px; color:#888;">
                                            <tr>
                                                <td>?????? ???? ????????</td>
                                                <td><strong>'${car.name}'</strong></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    ???????????? ???? ????????
                                                </td>
                                                <td>
                                                    <img style="width: 300px;" src="${car.imgUrl}" />
                                                </td>
                                            <tr>
                                            <tr>
                                                <td>???????? ???? ??????????????</td>
                                                <td><strong>'${BookingRequest.startDate.getProperFormat()}'</strong></td>
                                            </tr>
                                            <tr>
                                                <td>???????? ???? ??????????????</td>
                                                <td><strong>'${BookingRequest.endDate.getProperFormat()}'</strong></td>
                                            </tr>
                                            <tr>
                                                <td>?????????????? ???? ????????????</td>
                                                <td><strong>'${customer.phoneNumber}'</strong></td>
                                            </tr>                                            
                                            <tr>
                                                <td>Email ???? ????????????</td>
                                                <td><strong>'${customer.email}'</strong></td>
                                            </tr>

                                            <tr>
                                                <td>TOTAL ???????? + ??????????????????</td>
                                                <td><strong>&euro;'${BookingRequest.totalPrice}'</strong></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td height="30" bgcolor="#FFFFFF">&nbsp;</td>
                    </tr>
                    <tr>
                        <td height="30" bgcolor="#F8C807">&nbsp;</td>
                    </tr>
                    <tr>
                        <td bgcolor="#F8C807">
                            <table width="500" border="0" align="center" cellpadding="0" cellspacing="0"
                                   style="font-family:Arial, Helvetica, sans-serif;" widtd="350">
                                <tr>
                                    <td align="center"><h1
                                            style="color:#fff; font-size:24px; font-weight:bold; margin:0 0 15px 0;">
                                            RentAuto-Sofia</h1></td>
                                </tr>
                                <tr>
                                    <td align="center"><p
                                            style="padding-bottom: 20px; font-size:14px; color:#fff; margin:0; font-weight:normal; line-height:20px;">
                                        ???????? ???? ???????????????? ???????????????? ???? ??????:</p></td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <a href="http://rentauto-sofia.com/admin/bookRequest/${BookingRequest.id}/accept"
                                         style="color:#fff; margin:0; font-weight:normal; line-height:20px;background-color: #000;
                                            border: none;
                                            color: white;
                                            padding: 15px 32px;
                                            text-align: center;
                                            text-decoration: none;
                                            display: inline-block;
                                            font-size: 16px;
                                            margin: 4px 2px;
                                            cursor: pointer;">
                                            ???????????????? ????????????????
                                            </a>
                                       </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td height="30" bgcolor="#F8C807">&nbsp;</td>
                    </tr>
                    <tr>
                        <td bgcolor="#FFFFFF">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="center" bgcolor="#FFFFFF"
                            style="color:#b1b1b1; font-size:13px; font-family:Arial, Helvetica, sans-serif; font-weight:normal;">
                            &copy; 2018 RentAuto-Sofia | ???????????? ?????????? - ????????????????.
                        </td>
                    </tr>
                    <tr>
                        <td bgcolor="#FFFFFF">&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr valign="top">
            <td height="30" align="center" bgcolor="#eeeeee">&nbsp;</td>
        </tr>
    </table>
    </body>
    </html>'
            """
        mimeMessage.setContent(html, "text/html; charset=utf-8")

        helper.setTo("denbar@abv.bg")
        helper.setSubject("?????? ?????????? ???? ???????????? ????????!")

//        mimeMessage.writeTo(System.out)
        emailSender.send(mimeMessage)
    }

    fun informClientWith(BookingRequest: BookingRequest) {
        val car = carRepository.findOne(BookingRequest.carId)!!
        val customer = customerRepository.findOne(BookingRequest.customerId)!!

        val mimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, false, "UTF-8")


        val html =
                """
                <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<body style="margin:0">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" widtd="100%">
    <tr valign="top">
        <td height="30" align="center" bgcolor="#eeeeee">&nbsp;</td>
    </tr>
    <tr valign="top">
        <td align="center" bgcolor="#eeeeee">
            <table width="600" border="0" align="center" cellpadding="0" cellspacing="0" widtd="600">
                <tr>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#FFFFFF">
                        <table width="500" border="0" align="center" cellpadding="0" cellspacing="0"
                               style="font-family:Arial, Helvetica, sans-serif;" widtd="500">
                            <tr>
                                <td align="center" bgcolor="#333333" style="padding:15px 0"><h1
                                        style="color:#fff; font-size:24px; font-weight:bold; margin:0;">???????????????????? ???? ????????????</h1>
				</td>
                            </tr>
                            <tr>
                                <td align="left"><p
                                        style="font-size:14px; color:#555; font-weight:normal; line-height:20px;">
                                    <strong>?????????? ??????????????</strong><br>
                                    ???????????????????? ???? ???? ????????????????, ???????? ???? ?????????????????? ????.</p></td>
                            </tr>
                            <tr>
                                <td height="20"></td>
                            </tr>
                            <tr>
                                <td>
                                    <table width="500" border="0" cellspacing="0" cellpadding="5"
                                           style="font-size:14px; color:#888;">

                                        <tr>
                                            <td>?????? ???? ????????</td>
                                            <td><strong>'${car.name}'</strong></td>
                                        </tr>
                                        <tr>
                                            <td>???????? ???? ??????????????</td>
                                            <td><strong>'${BookingRequest.startDate.getProperFormat()}'</strong></td>
                                        </tr>
                                        <tr>
                                            <td>???????? ???? ??????????????</td>
                                            <td><strong>'${BookingRequest.endDate.getProperFormat()}'</strong></td>
                                        </tr>
                                        <tr>
                                            <td>??????????????</td>
                                            <td><strong>'${customer.phoneNumber}'</strong></td>
                                        </tr>                                            <tr>
                                            <td>Email</td>
                                            <td><strong>'${customer.email}'</strong></td>
                                        </tr>

                                        <tr>
                                            <td>TOTAL ????????</td>
                                            <td><strong>&euro;'${BookingRequest.totalPrice}'</strong></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                ???????????? ???? ????????
                                            </td>
                                            <td>
                                                <img style="width: 300px;" src="${car.imgUrl}" />
                                            </td>
                                        <tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="30" bgcolor="#FFFFFF">&nbsp;</td>
                </tr>
                <tr>
                    <td height="30" bgcolor="#F8C807">&nbsp;</td>
                </tr>
                <tr>
                    <td bgcolor="#F8C807">
                        <table width="500" border="0" align="center" cellpadding="0" cellspacing="0"
                               style="font-family:Arial, Helvetica, sans-serif;" widtd="350">
                            <tr>
                                <td align="center"><h1
                                        style="color:#fff; font-size:24px; font-weight:bold; margin:0 0 15px 0;">Car
                                    Rentals</h1></td>
                            </tr>
                            <tr>
                                <td align="center"><p
                                        style="font-size:14px; color:#fff; margin:0; font-weight:normal; line-height:20px;">
                                    ???????? ?????????????? ????, ???? ?????? ???????????? ???????????????????? ??????????, ???? ???? ?????????? ???? ???? ?????????????? ?? ??????.<br/>
                                    ???????? ???? ???? ???????????????? ?? ?????? ???? ??????:</p><a href="tel:+359899588830">+359899588830</a></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="30" bgcolor="#F8C807">&nbsp;</td>
                </tr>
                <tr>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#FFFFFF"
                        style="color:#b1b1b1; font-size:13px; font-family:Arial, Helvetica, sans-serif; font-weight:normal;">
                        &copy; 2018 Car Rentals | All Rights Reserved.
                    </td>
                </tr>
                <tr>
                    <td bgcolor="#FFFFFF">&nbsp;</td>
                </tr>
            </table>
        </td>
    </tr>
    <tr valign="top">
        <td height="30" align="center" bgcolor="#eeeeee">&nbsp;</td>
    </tr>
</table>
</body>
</html>'
            """
        mimeMessage.setContent(html, "text/html; charset=utf-8")

        helper.setTo(customer.email)
        helper.setSubject("?????????????? ????????????!")

//        mimeMessage.writeTo(System.out)
        emailSender.send(mimeMessage)
    }
}
