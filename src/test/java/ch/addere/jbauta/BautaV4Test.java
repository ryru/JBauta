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
 * JUnit tests for BautaV4
 *
 */
class BautaV4Test {

  private static BautaFactory factory;

  @BeforeAll
  static void beforeAll() {
    factory = new BautaFactory();
  }

  @Test
  void testIPv4ValidIPBitmask() {
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.0");
      BautaV4 bta = new BautaV4(ipv4);
      bta.maskAny(ipv4);

    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4InvalidMaskInput0() {
    try {
      BautaV4 bta = factory.createIPv4();
      InetAddress invalid = InetAddress.getByName("::");
      assertEquals(invalid, bta.maskAny(invalid));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4InvalidMaskInput1() {
    try {
      BautaV4 bta = factory.createIPv4();
      InetAddress invalid = InetAddress.getByName("::");
      assertEquals(invalid, bta.maskPublicRoutableOnly(invalid));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4InvalidIPBitmask() {
    Executable invalidIPBitmask = () -> {
      InetAddress ipv6 = InetAddress.getByName("::");
      BautaV4 bta = new BautaV4(ipv6);
      bta.maskAny(ipv6);
    };

    assertThrows(IllegalArgumentException.class, invalidIPBitmask);
  }

  @Test
  void testIPv4DefaultMasking0() {
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("255.255.255.255");
      InetAddress masked = InetAddress.getByName("255.255.240.0");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4DefaultMasking1() {
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("192.0.2.1");
      InetAddress masked = InetAddress.getByName("192.0.0.0");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4DefaultMasking2() {
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress masked = InetAddress.getByName("198.51.96.0");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4DefaultMasking3() {
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("203.0.113.1");
      InetAddress masked = InetAddress.getByName("203.0.112.0");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking0() {
    try {
      BautaV4 bta = factory.createIPv4(InetAddress.getByName("255.255.255.255"));
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress masked = InetAddress.getByName("198.51.100.1");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking1() {
    try {
      BautaV4 bta = factory.createIPv4(InetAddress.getByName("0.0.0.0"));
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress masked = InetAddress.getByName("0.0.0.0");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking2() {
    try {
      BautaV4 bta = factory.createIPv4(InetAddress.getByName("255.255.255.0"));
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress masked = InetAddress.getByName("198.51.100.0");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking3() {
    try {
      BautaV4 bta = factory.createIPv4(InetAddress.getByName("255.255.0.0"));
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress masked = InetAddress.getByName("198.51.0.0");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking4() {
    try {
      BautaV4 bta = factory.createIPv4(InetAddress.getByName("255.0.0.0"));
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress masked = InetAddress.getByName("198.0.0.0");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking5() {
    try {
      BautaV4 bta = factory.createIPv4(InetAddress.getByName("255.0.255.0"));
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress masked = InetAddress.getByName("198.0.100.0");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonDefaultMasking6() {
    try {
      BautaV4 bta = factory.createIPv4(InetAddress.getByName("0.255.0.255"));
      InetAddress ipv4 = InetAddress.getByName("198.51.100.1");
      InetAddress masked = InetAddress.getByName("0.51.0.1");
      assertEquals(masked, bta.maskAny(ipv4));
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
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("192.168.1.1");
      assertEquals(ipv4, bta.maskPublicRoutableOnly(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonRoutablePrivateSpaceC1() {
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("192.168.1.1");
      InetAddress masked = InetAddress.getByName("192.168.0.0");
      assertEquals(masked, bta.maskAny(ipv4));
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
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("172.16.1.1");
      assertEquals(ipv4, bta.maskPublicRoutableOnly(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonRoutablePrivateSpaceB1() {
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("172.16.1.1");
      InetAddress masked = InetAddress.getByName("172.16.0.0");
      assertEquals(masked, bta.maskAny(ipv4));
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
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("10.1.1.1");
      assertEquals(ipv4, bta.maskPublicRoutableOnly(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonRoutablePrivateSpaceA1() {
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("10.1.1.1");
      InetAddress masked = InetAddress.getByName("10.1.0.0");
      assertEquals(masked, bta.maskAny(ipv4));
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
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("127.1.1.1");
      assertEquals(ipv4, bta.maskPublicRoutableOnly(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonRoutableLocalnet1() {
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("127.1.1.1");
      InetAddress masked = InetAddress.getByName("127.1.0.0");
      assertEquals(masked, bta.maskAny(ipv4));
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
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("169.254.1.1");
      assertEquals(ipv4, bta.maskPublicRoutableOnly(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testIPv4NonRoutableZeroconfig1() {
    BautaV4 bta = factory.createIPv4();
    try {
      InetAddress ipv4 = InetAddress.getByName("169.254.1.1");
      InetAddress masked = InetAddress.getByName("169.254.0.0");
      assertEquals(masked, bta.maskAny(ipv4));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

}