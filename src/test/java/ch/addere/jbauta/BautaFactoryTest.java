/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BautaFactoryTest {

  private static BautaFactory factory;

  InetAddress aIPv4;
  InetAddress aIPv6;

  {
    try {
      aIPv4 = InetAddress.getByName("127.0.0.1");
      aIPv6 = InetAddress.getByName("::1");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }


  @BeforeAll
  public static void beforeAll() {
    factory = new BautaFactory();
  }

  @Test
  @DisplayName("Test assumed instantiation for default masking")
  void testAssumedInstantiation0() throws UnknownHostException {
    Bauta bta = factory.createDefaultIPMask();
    bta.maskAny(aIPv4);
  }

  @Test
  @DisplayName("Test assumed instantiation for custom bitmask masking")
  void testAssumedInstantiation1() throws UnknownHostException {
    Bauta bta = factory.createCustomIPMask(12, 42);
    bta.maskAny(aIPv4);
  }

  @Test
  @DisplayName("Test assumed instantiation for custom address masking")
  void testAssumedInstantiation2() throws UnknownHostException {
    Bauta bta = factory.createCustomIPMask(aIPv4, aIPv6);
    bta.maskAny(aIPv4);
  }

  @Test
  @DisplayName("Test return reference for default IP mask")
  void testReturnReference0() {
    Bauta bta = factory.createDefaultIPMask();
    assertNotNull(bta);
  }

  @Test
  @DisplayName("Test return reference for address IP mask")
  void testReturnReference1() {
    Bauta bta = factory.createCustomIPMask(aIPv4, aIPv6);
    assertNotNull(bta);
  }

  @Test
  @DisplayName("Test return reference for integer IP mask")
  void testReturnReference2() {
    Bauta bta = factory.createCustomIPMask(12, 42);
    assertNotNull(bta);
  }

  @Test
  @DisplayName("Test null initialisation")
  void testObjectCreation0() {
    Executable nullInitialisation = () -> {
      Bauta bta = factory.createCustomIPMask(null, null);
      bta.maskAny(aIPv4);
    };

    assertThrows(NullPointerException.class, nullInitialisation);
  }

  @Test
  @DisplayName("Test wrongly mixed initialisation")
  void testObjectCreation1() {
    Executable wrongInitialisation = () -> {
      Bauta bta = factory.createCustomIPMask(aIPv6, aIPv4);
      bta.maskAny(aIPv4);
    };

    assertThrows(IllegalArgumentException.class, wrongInitialisation);
  }

  @Test
  @DisplayName("Test too small IPv4 mask input")
  void testObjectCreationTooSmallV4() {
    Executable nullInitialisation = () -> {
      Bauta bta = factory.createCustomIPMask(-1, 42);
      bta.maskAny(aIPv4);
    };

    assertThrows(IllegalArgumentException.class, nullInitialisation);
  }

  @Test
  @DisplayName("Test too big IPv4 mask input")
  void testObjectCreationTooBigV4() {
    Executable nullInitialisation = () -> {
      Bauta bta = factory.createCustomIPMask(33, 42);
      bta.maskAny(aIPv4);
    };

    assertThrows(IllegalArgumentException.class, nullInitialisation);
  }

  @Test
  @DisplayName("Test too small IPv6 mask input")
  void testObjectCreationTooSmallV6() {
    Executable nullInitialisation = () -> {
      Bauta bta = factory.createCustomIPMask(12, -1);
      bta.maskAny(aIPv4);
    };

    assertThrows(IllegalArgumentException.class, nullInitialisation);
  }

  @Test
  @DisplayName("Test too big IPv6 mask input")
  void testObjectCreationTooBigV6() {
    Executable nullInitialisation = () -> {
      Bauta bta = factory.createCustomIPMask(12, 129);
      bta.maskAny(aIPv4);
    };

    assertThrows(IllegalArgumentException.class, nullInitialisation);
  }
}