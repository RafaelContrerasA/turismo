package com.example.Integradoraturismo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Integradoraturismo.http.PaymentIntentDTO;
import com.example.Integradoraturismo.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    /**
     * Crea un PaymentIntent en la API de Stripe.
     *
     * @param paymentIntentDto Un objeto que contiene los datos para crear un PaymentIntent.
     * @return El PaymentIntent creado en formato JSON.
     * @throws StripeException Si hubo un error al crear el PaymentIntent.
     */
    @PostMapping("/paymentintent")
    public ResponseEntity<String> payment(@RequestBody PaymentIntentDTO paymentIntentDto) throws StripeException {
        PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentDto);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    /**
     * Confirma un PaymentIntent en la API de Stripe.
     *
     * @param id El ID del PaymentIntent a confirmar.
     * @return El PaymentIntent confirmado en formato JSON.
     * @throws StripeException Si hubo un error durante la confirmaci n.
     */
    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirm(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = paymentService.confirm(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    /**
     * Cancela un PaymentIntent en la API de Stripe.
     *
     * @param id El ID del PaymentIntent a cancelar.
     * @return El PaymentIntent cancelado en formato JSON.
     * @throws StripeException Si hubo un error al cancelar el PaymentIntent.
     */
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = paymentService.cancel(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}
