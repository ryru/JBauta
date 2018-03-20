/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * JUnit tests for BautaV6
 */
class BautaV6Test {

  private static BautaFactory factory;

  @BeforeAll
  static void beforeAll() {
    factory = new BautaFactory();
  }

  @Test
  void testIPv6ValidIPBitmask() throws UnknownHostException {
    InetAddress ipv6 = InetAddress.getByName("::");
    BautaV6 bta = new BautaV6(ipv6);
    bta.maskAny(ipv6);
  }

  @Test
  void testIPv6InvalidMaskInput0() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6();
    InetAddress invalid = InetAddress.getByName("255.255.255.0");
    assertEquals(invalid, bta.maskAny(invalid));
  }

  @Test
  void testIPv6InvalidMaskInput1() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6();
    InetAddress invalid = InetAddress.getByName("255.255.255.0");
    assertEquals(invalid, bta.maskPublicRoutableOnly(invalid));
  }

  @Test
  void testIPv6InvalidIPBitmask() {
    Executable invalidIPBitmask = () -> {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.0");
      BautaV6 bta = new BautaV6(ipv4);
      bta.maskAny(ipv4);
    };

    assertThrows(IllegalArgumentException.class, invalidIPBitmask);
  }

  @Test
  void testIPv6DefaultMasking0() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6();
    InetAddress ipv6 = InetAddress
        .getByName("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff");
    InetAddress masked = InetAddress.getByName("ffff:ffff:0:0:0:0:0:0");
    assertEquals(masked, bta.maskAny(ipv6));
  }

  @Test
  void testIPv6DefaultMasking1() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6();
    InetAddress ipv6 = InetAddress.getByName("2001:DB8::");
    InetAddress masked = InetAddress.getByName("2001:DB8:0:0:0:0:0:0");
    assertEquals(masked, bta.maskAny(ipv6));
  }

  @Test
  void testIPv6DefaultMasking2() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6();
    InetAddress ipv6 = InetAddress.getByName("2001:DB8::1337");
    InetAddress masked = InetAddress.getByName("2001:DB8::");
    assertEquals(masked, bta.maskAny(ipv6));
  }

  @Test
  void testIPv6DefaultMasking3() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6();
    InetAddress ipv6 = InetAddress.getByName("2001:DB8::198.51.100.1");
    InetAddress masked = InetAddress.getByName("2001:DB8::");
    assertEquals(masked, bta.maskAny(ipv6));
  }

  @Test
  void testIPv6NonDefaultMasking0() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6(
        InetAddress.getByName("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff"));
    InetAddress ipv6 = InetAddress
        .getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
    InetAddress masked = InetAddress
        .getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
    assertEquals(masked, bta.maskAny(ipv6));
  }

  @Test
  void testIPv6NonDefaultMasking1() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6(InetAddress.getByName("::"));
    InetAddress ipv6 = InetAddress
        .getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
    InetAddress masked = InetAddress.getByName("::");
    assertEquals(masked, bta.maskAny(ipv6));
  }

  @Test
  void testIPv6NonDefaultMasking2() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6(InetAddress.getByName("ffff:ffff:ffff::"));
    InetAddress ipv6 = InetAddress
        .getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
    InetAddress masked = InetAddress.getByName("2001:DB8:ffff::");
    assertEquals(masked, bta.maskAny(ipv6));
  }

  @Test
  void testIPv6NonDefaultMasking3() throws UnknownHostException {
    BautaV6 bta = factory
        .createIPv6(InetAddress.getByName("ffff:ffff:ffff:0:ffff:0:ffff:0"));
    InetAddress ipv6 = InetAddress
        .getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
    InetAddress masked = InetAddress.getByName("2001:DB8:ffff:0:ffff:0:ffff:0");
    assertEquals(masked, bta.maskAny(ipv6));
  }

  /**
   * Localnet Address Space ::1/128
   * RFC 4291
   */
  @Test
  void testIPv6NonRoutableLocalnet0() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6();
    InetAddress ipv6 = InetAddress.getByName("::1");
    assertEquals(ipv6, bta.maskPublicRoutableOnly(ipv6));
  }

  @Test
  void testIPv6NonRoutableLocalnet1() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6();
    InetAddress ipv6 = InetAddress.getByName("::1");
    InetAddress masked = InetAddress.getByName("::");
    assertEquals(masked, bta.maskAny(ipv6));
  }

  /**
   * Link Local Address Space fe80::/64
   * RFC 4291
   */
  @Test
  void testIPv6NonRoutableLinkLocal0() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6();
    InetAddress ipv6 = InetAddress.getByName("fe80::1");
    assertEquals(ipv6, bta.maskPublicRoutableOnly(ipv6));
  }

  @Test
  void testIPv6NonRoutableLinkLocal1() throws UnknownHostException {
    BautaV6 bta = factory.createIPv6();
    InetAddress ipv6 = InetAddress.getByName("fe80::1");
    InetAddress masked = InetAddress.getByName("fe80::");
    assertEquals(masked, bta.maskAny(ipv6));
  }

}