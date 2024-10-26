package com.example.Integradoraturismo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.Integradoraturismo.http.PaymentIntentDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class PaymentService {
  @Value("${stripe.key.secret}")
  String secretKey;

  /**
   * Crea un PaymentIntent en la API de Stripe.
   *
   * @param paymentIntentDto Un objeto que contiene los datos para crear un PaymentIntent.
   * @return El PaymentIntent creado.
   * @throws StripeException Si hubo un error al crear el PaymentIntent.
   */
  public PaymentIntent paymentIntent(PaymentIntentDTO paymentIntentDto) throws StripeException {
    Stripe.apiKey = secretKey;
    Map<String, Object> params = new HashMap<>();
    params.put("amount", paymentIntentDto.getAmount());
    params.put("currency", paymentIntentDto.getCurrency().toString());
    params.put("description", paymentIntentDto.getDescription());
    
    ArrayList<String> paymentMethodTypes = new ArrayList<>();
    paymentMethodTypes.add("card");
    params.put("payment_method_types", paymentMethodTypes);
    
    return PaymentIntent.create(params);
  }

  /**
   * Confirms a PaymentIntent using the specified payment method.
   *
   * @param id The ID of the PaymentIntent to confirm.
   * @return The confirmed PaymentIntent.
   * @throws StripeException If there is an error during confirmation.
   */
  public PaymentIntent confirm(String id) throws StripeException {
    Stripe.apiKey = secretKey;
    PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
    Map<String, Object> params = new HashMap<>();
    params.put("payment_method", "pm_card_visa");
    paymentIntent.confirm(params);
    return paymentIntent;
  }

  /**
   * Cancela un PaymentIntent.
   *
   * @param id El ID del PaymentIntent que se va a cancelar.
   * @return El PaymentIntent cancelado.
   * @throws StripeException Si hubo un error al cancelar el PaymentIntent.
   */
  public PaymentIntent cancel(String id) throws StripeException {
    Stripe.apiKey = secretKey;
    PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
    paymentIntent.cancel();
    return paymentIntent;
  }

}
