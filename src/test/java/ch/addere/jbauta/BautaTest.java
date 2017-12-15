/*
 * Copyright 2017 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import org.junit.jupiter.api.Test;

/**
 * Created by pascal on 08.12.17.
 */
class BautaTest {

  @Test
  void testMaskNullReference() {
    assertThrows(InvalidParameterException.class, () -> {
      Bauta bta = new Bauta();
      bta.mask(null);
    });
  }

  @Test
  void testSetIPv4MaskNullReference() {
    assertThrows(InvalidParameterException.class, () -> {
      Bauta bta = new Bauta();
      bta.setIPv4Mask(null);
    });
  }

  @Test
  void testSetIPv6MaskNullReference() {
    assertThrows(InvalidParameterException.class, () -> {
      Bauta bta = new Bauta();
      bta.setIPv6Mask(null);
    });
  }


  /**
   * IPv4 Address tests
   */

  @Test
  void testIPv4DefaultMasking0() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress masked = InetAddress.getByName("255.255.240.0");
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4DefaultMasking1() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("192.0.2.1");
      InetAddress masked = InetAddress.getByName("192.0.0.0");
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4DefaultMasking2() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress masked = InetAddress.getByName("198.51.96.0");
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4DefaultMasking3() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("203.0.113.1");
      InetAddress masked = InetAddress.getByName("203.0.112.0");
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking0() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress mask = InetAddress.getByName("255.255.255.255");
      InetAddress masked = InetAddress.getByName("198.51.100.1");
      bta.setIPv4Mask((Inet4Address) mask);
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking1() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress mask = InetAddress.getByName("0.0.0.0");
      InetAddress masked = InetAddress.getByName("0.0.0.0");
      bta.setIPv4Mask((Inet4Address) mask);
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking2() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress mask = InetAddress.getByName("255.255.255.0");
      InetAddress masked = InetAddress.getByName("198.51.100.0");
      bta.setIPv4Mask((Inet4Address) mask);
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking3() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress mask = InetAddress.getByName("255.255.0.0");
      InetAddress masked = InetAddress.getByName("198.51.0.0");
      bta.setIPv4Mask((Inet4Address) mask);
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking4() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress mask = InetAddress.getByName("255.0.0.0");
      InetAddress masked = InetAddress.getByName("198.0.0.0");
      bta.setIPv4Mask((Inet4Address) mask);
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking5() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress mask = InetAddress.getByName("255.0.255.0");
      InetAddress masked = InetAddress.getByName("198.0.100.0");
      bta.setIPv4Mask((Inet4Address) mask);
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking6() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress mask = InetAddress.getByName("0.255.0.255");
      InetAddress masked = InetAddress.getByName("0.51.0.1");
      bta.setIPv4Mask((Inet4Address) mask);
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  /**
   * Private Address Space 192.168.0.0/24
   * RFC 1918
   */
  @Test
  void testIPv4NonRoutablePrivateSpaceC0() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("192.168.1.1");
      assertEquals(ipv4, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonRoutablePrivateSpaceC1() {
    Bauta bta = new Bauta();
    bta.maskNonRoutableAddress();
    try {
      InetAddress ipv4 = InetAddress.getByName("192.168.1.1");
      InetAddress masked = InetAddress.getByName("192.168.0.0");
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  /**
   * Private Address Space 172.16.0.0/16
   * RFC 1918
   */
  @Test
  void testIPv4NonRoutablePrivateSpaceB0() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("172.16.1.1");
      assertEquals(ipv4, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonRoutablePrivateSpaceB1() {
    Bauta bta = new Bauta();
    bta.maskNonRoutableAddress();
    try {
      InetAddress ipv4 = InetAddress.getByName("172.16.1.1");
      InetAddress masked = InetAddress.getByName("172.16.0.0");
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  /**
   * Private Address Space 10.0.0.0/8
   * RFC 1918
   */
  @Test
  void testIPv4NonRoutablePrivateSpaceA0() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("10.1.1.1");
      assertEquals(ipv4, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonRoutablePrivateSpaceA1() {
    Bauta bta = new Bauta();
    bta.maskNonRoutableAddress();
    try {
      InetAddress ipv4 = InetAddress.getByName("10.1.1.1");
      InetAddress masked = InetAddress.getByName("10.1.0.0");
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  /**
   * Localnet Address Space 127.0.0.1/8
   * RFC 3330
   */
  @Test
  void testIPv4NonRoutableLocalnet0() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("127.1.1.1");
      assertEquals(ipv4, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonRoutableLocalnet1() {
    Bauta bta = new Bauta();
    bta.maskNonRoutableAddress();
    try {
      InetAddress ipv4 = InetAddress.getByName("127.1.1.1");
      InetAddress masked = InetAddress.getByName("127.1.0.0");
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  /**
   * Zeroconfig Address Space 169.154.0.0/16
   * RFC 3927
   */
  @Test
  void testIPv4NonRoutableZeroconfig0() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv4 = InetAddress.getByName("169.254.1.1");
      assertEquals(ipv4, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonRoutableZeroconfig1() {
    Bauta bta = new Bauta();
    bta.maskNonRoutableAddress();
    try {
      InetAddress ipv4 = InetAddress.getByName("169.254.1.1");
      InetAddress masked = InetAddress.getByName("169.254.0.0");
      assertEquals(masked, bta.mask(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }


  /**
   * IPv6 Address tests
   */
  @Test
  void testIPv6DefaultMasking0() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv6 = InetAddress.getByName("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff");
      InetAddress masked = InetAddress.getByName("ffff:ffff:0:0:0:0:0:0");
      assertEquals(masked, bta.mask(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6DefaultMasking1() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv6 = InetAddress.getByName("2001:DB8::");
      InetAddress masked = InetAddress.getByName("2001:DB8:0:0:0:0:0:0");
      assertEquals(masked, bta.mask(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6DefaultMasking2() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv6 = InetAddress.getByName("2001:DB8::1337");
      InetAddress masked = InetAddress.getByName("2001:DB8::");
      assertEquals(masked, bta.mask(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6DefaultMasking3() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv6 = InetAddress.getByName("2001:DB8::198.51.100.1");
      InetAddress masked = InetAddress.getByName("2001:DB8::");
      assertEquals(masked, bta.mask(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonDefaultMasking0() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv6 = InetAddress.getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
      InetAddress mask = InetAddress.getByName("ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff");
      InetAddress masked = InetAddress.getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
      bta.setIPv6Mask((Inet6Address) mask);
      assertEquals(masked, bta.mask(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonDefaultMasking1() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv6 = InetAddress.getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
      InetAddress mask = InetAddress.getByName("::");
      InetAddress masked = InetAddress.getByName("::");
      bta.setIPv6Mask((Inet6Address) mask);
      assertEquals(masked, bta.mask(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonDefaultMasking2() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv6 = InetAddress.getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
      InetAddress mask = InetAddress.getByName("ffff:ffff:ffff::");
      InetAddress masked = InetAddress.getByName("2001:DB8:ffff::");
      bta.setIPv6Mask((Inet6Address) mask);
      assertEquals(masked, bta.mask(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonDefaultMasking3() {
    Bauta bta = new Bauta();
    try {
      InetAddress ipv6 = InetAddress.getByName("2001:DB8:ffff:ffff:ffff:ffff:ffff:ffff");
      InetAddress mask = InetAddress.getByName("ffff:ffff:ffff:0:ffff:0:ffff:0");
      InetAddress masked = InetAddress.getByName("2001:DB8:ffff:0:ffff:0:ffff:0");
      bta.setIPv6Mask((Inet6Address) mask);
      assertEquals(masked, bta.mask(ipv6));
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
    Bauta bta = new Bauta();
    try {
      InetAddress ipv6 = InetAddress.getByName("::1");
      assertEquals(ipv6, bta.mask(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonRoutableLocalnet1() {
    Bauta bta = new Bauta();
    bta.maskNonRoutableAddress();
    try {
      InetAddress ipv6 = InetAddress.getByName("::1");
      InetAddress masked = InetAddress.getByName("::");
      assertEquals(masked, bta.mask(ipv6));
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
    Bauta bta = new Bauta();
    try {
      InetAddress ipv6 = InetAddress.getByName("fe80::1");
      assertEquals(ipv6, bta.mask(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv6NonRoutableLinkLocal1() {
    Bauta bta = new Bauta();
    bta.maskNonRoutableAddress();
    try {
      InetAddress ipv6 = InetAddress.getByName("fe80::1");
      InetAddress masked = InetAddress.getByName("fe80::");
      assertEquals(masked, bta.mask(ipv6));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

}