/*
 * Copyright 2017 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.jetbrains.annotations.NotNull;

/**
 * Bauta Factory for creating IPv4 or IPv6 masquerading context.
 *
 */
public class BautaFactory {

  private static final byte[] IPV4_DEFAULT_MASK = {
      (byte) 0xFF, (byte) 0xFF, (byte) 0xF0, (byte) 0x00};
  private static final byte[] IPV6_DEFAULT_MASK = {
      (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

  /**
   * Create an IPv4 default bitmask context.
   *
   * An instance of BautaV4 with a default bitmask truncates the last 12 bit of a given address. Therefor the default IPv4 bitmask is set to 255.255.240.0 or 0xFFFFF000 in hex notation. This is the privacy by default method.
   *
   * @return Instance of BautaV4 with default bitmask
   */
  public BautaV4 createIPv4() {
    try {
      return new BautaV4(InetAddress.getByAddress(IPV4_DEFAULT_MASK));
    } catch (UnknownHostException e) {
      throw new AssertionError(
          "this should never happen: the IP address is supposed to be always valid", e);
    }
  }

  /**
   * Create an IPv4 context with a non-default bitmask context on address base.
   *
   * By setting the appropriate bitmask this method allows to control every single bit of a given IPv4 to masquerade. This method allows to create own bitmask to masquerade IPv4 with.
   *
   * <p>Example 1: A bitmask address set to 255.255.255.255 will not modify any address while a bitmask address set to 0.0.0.0 will always return 0.0.0.0 regardless of the input IPv4 address.</p>
   *
   * <p>Example 2: A bitmask address set to 255.240.0.0 will only keep the first 12 bits of a given IPv4 address to mask.</p>
   *
   * @param maskingAddress Bitmask address to use for masquerading
   * @return Instance of BautaV4 with non-default bitmask
   */
  public BautaV4 createIPv4(@NotNull final InetAddress maskingAddress) {

    return new BautaV4(maskingAddress);
  }

  /**
   * Create an IPv4 context with a non-default bitmask context on CIDR notation base.
   *
   * This method allows to create own bitmask based on CIDR notation to masquerade IPv4 with.
   *
   * <p>Example 1: A bitmask address set to 32 will result in an address bitmask of 255.255.255.255 and therefor not modify any address while a bitmask address set to 0 will always return 0.0.0.0 regardless of the input IPv4 address.</p>
   *
   * <p>Example 2: A bitmask address set to 12 will only keep the first 12 bits of a given IPv4 address to mask.</p>
   *
   * @param slashBitmask Bitmask in CIDR notation to use for masquerading
   * @return Instance of BautaV4 with non-default bitmask
   */
  public BautaV4 createIPv4(final int slashBitmask) {
    if (slashBitmask < 0 || slashBitmask > 32) {
      throw new IllegalArgumentException(
          "Error: Slash should be between 0 and 32 for IP version 4 addresses");
    }

    byte[] octets = new byte[4];
    int amountOfFullySetOctets = slashBitmask / 8;
    int partiallySetBitsInLastOctet = slashBitmask % 8;

    int i;
    for (i = 0; i < amountOfFullySetOctets; i++) {
      octets[i] = setByte(8);
    }

    if (partiallySetBitsInLastOctet != 0) {
      octets[i] = setByte(partiallySetBitsInLastOctet);
    }

    try {
      InetAddress maskingAddress = InetAddress.getByAddress(octets);
      return new BautaV4(maskingAddress);
    } catch (UnknownHostException e) {
      throw new AssertionError(
          "this should never happen: the IP address is supposed to be always valid", e);
    }
  }

  /**
   * Create an IPv6 default bitmask context.
   *
   * An instance of BautaV6 with a default bitmask truncates the last 96 bit of a given address. Therefor the default IPv6 bitmask is set to FFFF:FFFF:: in IPv6 short notation. This is the privacy by default method.
   *
   * @return Instance of BautaV6 with default bitmask
   */
  public BautaV6 createIPv6() {
    try {
      return new BautaV6(InetAddress.getByAddress(IPV6_DEFAULT_MASK));
    } catch (UnknownHostException e) {
      throw new AssertionError(
          "this should never happen: the IP address is supposed to be always valid", e);
    }
  }

  /**
   * Create an IPv6 context with a non-default bitmask context.
   *
   * By setting the appropriate bitmask this method allows to control every single bit of a given IPv6 to masquerade. This method allows to create own bitmask to masquerade IPv6 with.
   *
   * <p>Example 1: A bitmask address set to FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF will not modify any address while a bitmask address set to :: will always return :: regardless of the input IPv6 address.</p>
   *
   * <p>Example 2: A bitmask address set to FFF0:: will only keep the first 12 bits of a given IPv6 address to mask.</p>
   *
   * @param maskingAddress Bitmask address to use for masquerading
   * @return Instance of BautaV6 with non-default bitmask
   */
  public BautaV6 createIPv6(@NotNull final InetAddress maskingAddress) {

    return new BautaV6(maskingAddress);
  }

  /**
   * Create an IPv6 context with a non-default bitmask context on CIDR notation base.
   *
   * This method allows to create own bitmask based on CIDR notation to masquerade IPv6 with.
   *
   * <p>Example 1: A bitmask address set to 128 will result in an address bitmask of FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF and therefor not modify any address while a bitmask address set to 0 will always return :: regardless of the input IPv6 address.</p>
   *
   * <p>Example 2: A bitmask address set to 12 will only keep the first 12 bits of a given IPv6 address to mask.</p>
   *
   * @param slashBitmask Bitmask in CIDR notation to use for masquerading
   * @return Instance of BautaV4 with non-default bitmask
   */
  public BautaV6 createIPv6(final int slashBitmask) {
    if (slashBitmask < 0 || slashBitmask > 128) {
      throw new IllegalArgumentException(
          "Error: Slash should be between 0 and 128 for IP version 6 addresses");
    }

    byte[] octets = new byte[16];
    int amountOfFullySetOctets = slashBitmask / 8;
    int partiallySetBitsInLastOctet = slashBitmask % 8;

    int i;
    for (i = 0; i < amountOfFullySetOctets; i++) {
      octets[i] = setByte(8);
    }

    if (partiallySetBitsInLastOctet != 0) {
      octets[i] = setByte(partiallySetBitsInLastOctet);
    }

    try {
      InetAddress maskingAddress = InetAddress.getByAddress(octets);
      return new BautaV6(maskingAddress);
    } catch (UnknownHostException e) {
      throw new AssertionError(
          "this should never happen: the IP address is supposed to be always valid", e);
    }
  }

  private byte setByte(int setBits) {
    int newByte = 0xFF;

    newByte = newByte >> setBits;
    newByte = ~newByte;
    newByte = newByte & 0xFF;

    return (byte) newByte;
  }

}
