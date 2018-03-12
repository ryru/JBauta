/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for BautaV6
 *
 */
class BautaV6Test {

  private static BautaFactory factory;

  @BeforeAll
  public static void beforeAll() {
    factory = new BautaFactory();
  }

  @Test
  void testMaskNullReference0() {
    assertThrows(IllegalArgumentException.class, () -> {
      BautaV4 bta = factory.createIPv4();
      bta.maskAny(null);
    });
  }

  @Test
  void testMaskNullReference1() {
    assertThrows(IllegalArgumentException.class, () -> {
      BautaV4 bta = factory.createIPv4();
      bta.maskPublicRoutableOnly(null);
    });
  }


  @Test
  void testIPv6DefaultMasking0() {
    BautaV6 bta = factory.createIPv6();
    try {
      Inet6Address ipv6 = (Inet6Address) InetAddress
          .getByName("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff");
      Inet6Address masked = (Inet6Address) InetAddress.getByName("ffff:ffff:0:0:0:0:0:0");
      assertEquals(masked, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6DefaultMasking1() {
    BautaV6 bta = factory.createIPv6();
    try {
      Inet6Address ipv6 = (Inet6Address) InetAddress.getByName("2001:DB8::");
      Inet6Address masked = (Inet6Address) InetAddress.getByName("2001:DB8:0:0:0:0:0:0");
      assertEquals(masked, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6DefaultMasking2() {
    BautaV6 bta = factory.createIPv6();
    try {
      Inet6Address ipv6 = (Inet6Address) InetAddress.getByName("2001:DB8::1337");
      Inet6Address masked = (Inet6Address) InetAddress.getByName("2001:DB8::");
      assertEquals(masked, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6DefaultMasking3() {
    BautaV6 bta = factory.createIPv6();
    try {
      Inet6Address ipv6 = (Inet6Address) InetAddress.getByName("2001:DB8::198.51.100.1");
      Inet6Address masked = (Inet6Address) InetAddress.getByName("2001:DB8::");
      assertEquals(masked, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonDefaultMasking0() {
    try {
      BautaV6 bta = factory.createIPv6(
          (Inet6Address) InetAddress.getByName("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff"));
      Inet6Address ipv6 = (Inet6Address) InetAddress
          .getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
      Inet6Address masked = (Inet6Address) InetAddress
          .getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
      assertEquals(masked, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonDefaultMasking1() {
    try {
      BautaV6 bta = factory.createIPv6((Inet6Address) InetAddress.getByName("::"));
      Inet6Address ipv6 = (Inet6Address) InetAddress
          .getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
      Inet6Address masked = (Inet6Address) InetAddress.getByName("::");
      assertEquals(masked, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonDefaultMasking2() {
    try {
      BautaV6 bta = factory.createIPv6((Inet6Address) InetAddress.getByName("ffff:ffff:ffff::"));
      Inet6Address ipv6 = (Inet6Address) InetAddress
          .getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
      Inet6Address masked = (Inet6Address) InetAddress.getByName("2001:DB8:ffff::");
      assertEquals(masked, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonDefaultMasking3() {
    try {
      BautaV6 bta = factory
          .createIPv6((Inet6Address) InetAddress.getByName("ffff:ffff:ffff:0:ffff:0:ffff:0"));
      Inet6Address ipv6 = (Inet6Address) InetAddress
          .getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
      Inet6Address masked = (Inet6Address) InetAddress.getByName("2001:DB8:ffff:0:ffff:0:ffff:0");
      assertEquals(masked, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  /**
   * Localnet Address Space ::1/128
   * RFC 4291
   */
  @Test
  void testIPv6NonRoutableLocalnet0() {
    BautaV6 bta = factory.createIPv6();
    try {
      Inet6Address ipv6 = (Inet6Address) InetAddress.getByName("::1");
      assertEquals(ipv6, bta.maskPublicRoutableOnly(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonRoutableLocalnet1() {
    BautaV6 bta = factory.createIPv6();
    try {
      Inet6Address ipv6 = (Inet6Address) InetAddress.getByName("::1");
      Inet6Address masked = (Inet6Address) InetAddress.getByName("::");
      assertEquals(masked, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  /**
   * Link Local Address Space fe80::/64
   * RFC 4291
   */
  @Test
  void testIPv6NonRoutableLinkLocal0() {
    BautaV6 bta = factory.createIPv6();
    try {
      Inet6Address ipv6 = (Inet6Address) InetAddress.getByName("fe80::1");
      assertEquals(ipv6, bta.maskPublicRoutableOnly(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonRoutableLinkLocal1() {
    BautaV6 bta = factory.createIPv6();
    try {
      Inet6Address ipv6 = (Inet6Address) InetAddress.getByName("fe80::1");
      Inet6Address masked = (Inet6Address) InetAddress.getByName("fe80::");
      assertEquals(masked, bta.maskAny(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

}