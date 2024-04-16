<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="invoice" page-height="29.7cm" page-width="21cm"
                    margin-top="2.5cm"
                    margin-bottom="2.5cm"
                    margin-left="2.5cm"
                    margin-right="2.5cm">
                    <fo:region-body margin-top="0cm" margin-bottom="0cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="invoice">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="12pt" font-family="Arial">
                        <!-- Cabecera -->
                        <xsl:value-of select="factura/cabecera/nombre"/><fo:block/>
                        <xsl:value-of select="factura/cabecera/rfc"/><fo:block/>
                        <xsl:value-of select="factura/cabecera/sucursal"/><fo:block/>
                        <xsl:value-of select="factura/cabecera/direccion"/><fo:block/>
                        <xsl:value-of select="factura/cabecera/leyenda"/><fo:block/>
                        <xsl:value-of select="factura/cabecera/ciudad"/><fo:block/>
                        <fo:block/><fo:block/> Fecha: <xsl:value-of
                            select="concat(factura/fecha/dia, ' de ', factura/fecha/mes, ' de ', factura/fecha/ano)"/><fo:block/>
                        <fo:block/><fo:block/> Cliente: <xsl:value-of
                            select="factura/cliente/nombre"/><fo:block/> Membresía: <xsl:value-of
                            select="factura/cliente/membresia"/><fo:block/> Número de cliente:
                            <xsl:value-of select="factura/cliente/numero"/><fo:block/> Email:
                            <xsl:value-of select="factura/cliente/email"/><fo:block/>
                        <fo:block/><fo:block/> Servicio: <xsl:value-of
                            select="factura/servicio/tipo"/><fo:block/> Llegada: <xsl:value-of
                            select="factura/servicio/llegada"/><fo:block/> Salida: <xsl:value-of
                            select="factura/servicio/salida"/><fo:block/> Automóvil: <xsl:value-of
                            select="concat(factura/servicio/automovil/marca, ' - ', factura/servicio/automovil/matricula)"
                        /><fo:block/> Precio por hora: <xsl:value-of
                            select="factura/servicio/precioHora"/><fo:block/>
                        <fo:block/><fo:block/> Cobro: <xsl:value-of select="factura/cobro/moneda"/>
                        <xsl:value-of select="factura/cobro/subTotal"/><fo:block/> IVA:
                            <xsl:value-of select="factura/cobro/ivaPorcentaje"/>%<fo:block/> Total:
                            <xsl:value-of select="factura/cobro/total"/><fo:block/> Tipo de pago:
                            <xsl:value-of select="factura/cobro/tipoPago"/><fo:block/> Tarjeta:
                            <xsl:value-of
                            select="concat('**** **** **** ', factura/cobro/tarjeta/terminacionNumero)"
                        /><fo:block/> Titular: <xsl:value-of select="factura/cobro/tarjeta/titular"
                        /><fo:block/> Dirección de facturación: <xsl:value-of
                            select="factura/cobro/tarjeta/direccionFacturacion"/><fo:block/> Folio:
                            <xsl:value-of select="factura/cobro/folio"/><fo:block/>
                        <fo:block/><fo:block/> Contacto: <xsl:value-of
                            select="factura/contacto/leyenda"/><fo:block/> Teléfono: <xsl:value-of
                            select="factura/contacto/numeroTelefonico"/><fo:block/> Email:
                            <xsl:value-of select="factura/contacto/email"/><fo:block/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>
