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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BautaTest {

  InetAddress aIPv4;
  InetAddress aIPv6;

  {
    try {
      aIPv4 = InetAddress.getByName("192.0.2.42");
      aIPv6 = InetAddress.getByName("2001:DB8::42");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  private static BautaFactory factory;

  @BeforeAll
  static void beforeAll() {
    factory = new BautaFactory();
  }

  /*
   * Initialisation tests
   */
  @Test
  @DisplayName("Test null reference initialisation")
  void testObjectCreation() {
    Executable nullInitialisation = () -> {
      Bauta bta = new Bauta(null, null);
      bta.maskAny(aIPv4);
    };

    assertThrows(NullPointerException.class, nullInitialisation);
  }

  @Test
  @DisplayName("Test null reference masking")
  void testNullMasking() {
    Executable nullInitialisation = () -> {
      Bauta bta = factory.createDefaultIPMask();
      bta.maskAny(null);
    };

    assertThrows(NullPointerException.class, nullInitialisation);
  }

  /*
   * IP version 4 tests
   */
  @Test
  @DisplayName("Test proper IP version 4 masking")
  void testProperIPv4Masking0() throws UnknownHostException {
    Bauta bta = factory.createDefaultIPMask();
    InetAddress result = bta.maskAny(aIPv4);
    InetAddress expected = InetAddress.getByName("192.0.0.0");
    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Test proper IP version 4 masking with custom bitmask")
  void testProperIPv4Masking1() throws UnknownHostException {
    Bauta bta = factory.createCustomIPMask(30, 126);
    InetAddress result = bta.maskAny(aIPv4);
    InetAddress expected = InetAddress.getByName("192.0.2.40");
    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Test proper IP version 4 masking with custom ip address mask")
  void testProperIPv4Masking2() throws UnknownHostException {
    InetAddress ipv4Mask = InetAddress.getByName("0.255.255.255");
    InetAddress ipv6Mask = InetAddress.getByName("0:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF");
    Bauta bta = factory.createCustomIPMask(ipv4Mask, ipv6Mask);
    InetAddress result = bta.maskAny(aIPv4);
    InetAddress expected = InetAddress.getByName("0.0.2.42");
    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Test public routable IP version 4 masking")
  void testPublicRoutableIPv4Masking0() throws UnknownHostException {
    Bauta bta = factory.createDefaultIPMask();
    InetAddress result = bta.maskPublicRoutableOnly(aIPv4);
    InetAddress expected = InetAddress.getByName("192.0.0.0");
    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Test non public routable IP version 4 masking")
  void testPublicRoutableIPv4Masking1() throws UnknownHostException {
    InetAddress nonPublicRoutable = InetAddress.getByName("192.168.0.42");
    Bauta bta = factory.createDefaultIPMask();
    InetAddress result = bta.maskPublicRoutableOnly(nonPublicRoutable);
    InetAddress expected = InetAddress.getByName("192.168.0.42");
    assertEquals(expected, result);
  }

  /*
   * IP version 6 tests
   */
  @Test
  @DisplayName("Test proper IP version 6 masking")
  void testProperIPv6Masking0() throws UnknownHostException {
    Bauta bta = factory.createDefaultIPMask();
    InetAddress result = bta.maskAny(aIPv6);
    InetAddress expected = InetAddress.getByName("2001:DB8::");
    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Test proper IP version 6 masking with custom bitmask")
  void testProperIPv6Masking1() throws UnknownHostException {
    Bauta bta = factory.createCustomIPMask(30, 126);
    InetAddress result = bta.maskAny(aIPv6);
    InetAddress expected = InetAddress.getByName("2001:DB8::40");
    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Test proper IP version 6 masking with custom ip address mask")
  void testProperIPv6Masking2() throws UnknownHostException {
    InetAddress ipv4Mask = InetAddress.getByName("0.255.255.255");
    InetAddress ipv6Mask = InetAddress.getByName("0:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF");
    Bauta bta = factory.createCustomIPMask(ipv4Mask, ipv6Mask);
    InetAddress result = bta.maskAny(aIPv6);
    InetAddress expected = InetAddress.getByName("0:DB8::42");
    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Test public routable IP version 6 masking")
  void testPublicRoutableIPv6Masking0() throws UnknownHostException {
    Bauta bta = factory.createDefaultIPMask();
    InetAddress result = bta.maskPublicRoutableOnly(aIPv6);
    InetAddress expected = InetAddress.getByName("2001:DB8::");
    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Test non public routable IP version 6 masking")
  void testPublicRoutableIPv6Masking1() throws UnknownHostException {
    InetAddress nonPublicRoutable = InetAddress.getByName("::1");
    Bauta bta = factory.createDefaultIPMask();
    InetAddress result = bta.maskPublicRoutableOnly(nonPublicRoutable);
    InetAddress expected = InetAddress.getByName("::1");
    assertEquals(expected, result);
  }

}