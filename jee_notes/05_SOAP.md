# SOAP = Simple Object Access Protocol

Try to think of SOAP as **RPC-style messaging over HTTP**
where:

1. **Contract (WSDL + XSD)** --> the center of the SOAP
universe.

2. **Payload** -> always an XML document wrapped in a **SOAP
envelope**.

## SOAP = Message + Envelope + Contract

1. **SOAP message** -> an XML document with a fixed wrapper
structure:
    - Envelope (required)
    - Header (optional)
    - Body (optional)
2. **Contract-first** -> (common). You define a **WSDL**
(a service contract) and **XSD** files (schemas) first,
then generate Java classes or implement endpoints to match.
3. **Operations** -> more explicit than REST resources:
    - SOAP: `call GetCustomer` (REST: `GET /customers/{id}` )

## SOAP Extras (WS*)

SOAP ecosystems often use standards like:
- **WS-Security** (signing, encryption, tokens)
- **WS-Addressing**
- **Reliable Messaging**

In REST, these features are often handled differently
(OAuth, JWT, TLS, etc.) but SOAP has formal standards for many
enterprise needs.

# The core SOAP XML structure

Here is a typical SOAP 1.1 message:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:dom="http://example.com/domain">
    <soapenv:Header>
        <!-- Optional: security, correlation IDs, etc. -->
    </soapenv:Header>
    <soapenv:Body>
        <dom:SomeOperationRequest>
            <dom:customerId>123456</dom:customerId>
        </dom:SomeOperationRequest>
    </soapenv:Body>

</soapenv:Envelope>
```

- `soapenv` is just a namespace prefix. The most important part
is the **namespace URI**.
- Your **business payload** is inside the Body (`soapenv:Body`)

Now, in proper SOAP, the root element is usually:
`<soapenv:Envelope>`, but some teams store the inner
request/response XML separately (for logging, testing or
templating purposes).

- Layer A -> SOAP transport wrapper (standard)
    - Envelope/Header/Body
    - Optional WS-Security headers
    - Routing metadata

- Later B -> Domain payload
    - Your business root element (Example: "SOAP_Domain_Msg")
    - Internal fields like "user", "operationCode", "status",etc.

## SOAP in Java: the two common stacks

1. Spring Web Services (Spring-WS)
    - Focused on **contract-first** SOAP and XML marshalling.
    - You implement an endpoint method that receives/returns JAXB-generated objects.
    - Does not rely on JAX-WS runtime in the same way.  

Typical pattern:
- XSD defines request/response types.
- Spring-WS generates or maps those to Java classes.
- You write an @Endpoint with @PayloadRoot(...)

2. JAX-WS (Jakarta or older Java EE style)
    - Tools like `wsimport` generate Java stubs from WSDL.
    - You may implement @WebService or call remote services
    via generated client.

In modern Spring Boot, **Spring-WS** is usually the most
"Spring-native" approach.

# How SOAP maps to Spring Boot code

## Server side (you expose a SOAP service)

1. **Contract** (WSDL + XSD) define operations and types.
2. Spring Boot config exposes:
    - An endpoint URL like `/ws`
    - A generated WSDL like `/ws/service.wsdl`
3. You implement an endpoint:
    - It receives a request object (marshalled from XML)
    - It returns a response object (marshalled back to XML)

SOAP Request XML --> JAXB object --> Java method() 
--> JAXB repsonse --> SOAP Response XML.

## Client side (you call a SOAP service)

- You typically generate Java stubs from WSDL or use Spring's
`WebServiceTemplate`.
- You send XML (or objects marshalled to XML).
- You receive XML (or objects unmarshalled from XML).

# SOAP Faults (Error Responses)

Where REST returns: `400 Bad Request` with JSON.

SOAP often returns: `500 Server error` with a structured
`<soapenv:Fault>` in the Body:
```xml
<soapenv:Fault>
    <faultcode>soapenv:Client</faultcode>
    <faultstring>Invalid customerID</faultstring>
</soapenv:Fault>
```
So, the real error details are inside the SOAP Fault XML.

### Full SOAP message = SOAP envelope + Header + Body

