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