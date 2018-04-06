/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Helper class for working with IP addresses.
 */
public class AddressUtil {

  private static final int IPV4_BYTE_COUNT = 4;
  private static final int IPV6_BYTE_COUNT = 16;
  private static final int IP_CIDR_MINIMUM = 0;
  private static final int IPV4_CIDR_MAXIMUM = 32;
  private static final int IPV6_CIDR_MAXIMUM = 128;

  /**
   * Get length of a given IP address.
   *
   * <p>Precondition: {@code address} it not equals null</p>
   *
   * @param address Any IP address
   * @return Length of a given IP address
   */
  protected static int getAddressLength(final InetAddress address) {
    return address.getAddress().length;
  }

  /**
   * Check if IP address is an IP version 4 address.
   *
   * <p>Precondition: {@code address} it not equals null</p>
   *
   * @param address Any IP address
   * @return True if {@code address} is an IP version 4 address
   */
  protected static boolean isIPv4(final InetAddress address) {
    int addressByteCount = getAddressLength(address);
    boolean isIPv4 = true;

    if (addressByteCount != IPV4_BYTE_COUNT) {
      isIPv4 = false;
    }

    return isIPv4;
  }

  /**
   * Check if IP address is an IP version 6 address.
   *
   * <p>Precondition: {@code address} it not equals null</p>
   *
   * @param address Any IP address
   * @return True if {@code address} is an IP version 6 address
   */
  protected static boolean isIPv6(final InetAddress address) {
    int addressByteCount = getAddressLength(address);
    boolean isIPv6 = true;

    if (addressByteCount != IPV6_BYTE_COUNT) {
      isIPv6 = false;
    }

    return isIPv6;
  }

  /**
   * Check if CIDR-style notation is valid for IP version 4 addresses.
   *
   * @param ipv4CIDR CIDR-style integer
   * @return True if {@code ipv4CIDR} is between 0 and 32
   */
  protected static boolean isValidIPv4CIDR(int ipv4CIDR) {
    boolean isValidCIDR;

    isValidCIDR = (ipv4CIDR >= IP_CIDR_MINIMUM) && (ipv4CIDR <= IPV4_CIDR_MAXIMUM);

    return isValidCIDR;
  }

  /**
   * Check if CIDR-style notation is valid for IP version 6 addresses.
   *
   * @param ipv6CIDR CIDR-style integer
   * @return True if {@code ipv6CIDR} is between 0 and 128
   */
  protected static boolean isValidIPv6CIDR(int ipv6CIDR) {
    boolean isValidCIDR;

    isValidCIDR = (ipv6CIDR >= IP_CIDR_MINIMUM) && (ipv6CIDR <= IPV6_CIDR_MAXIMUM);

    return isValidCIDR;
  }

  /**
   * Create a valid IP version 4 address from a bitmask.
   *
   * A valid bitmask is between 0 and 32.
   *
   * @param bitmask A bitmask
   * @return InetAddress object with an IP address based on the bitmask
   */
  protected static InetAddress createIPv4FromBitmask(int bitmask) {
    if (AddressUtil.isValidIPv4CIDR(bitmask)) {
      throw new IllegalArgumentException("Invalid IPv4 bitmask. Has to be between 0 and 32");
    }

    InetAddress address = null;
    try {
      address = setBitsFromLeftToRight(IPV4_BYTE_COUNT, bitmask);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

    return address;
  }

  /**
   * Create a valid IP version 6 address from a bitmask.
   *
   * A valid bitmask is between 0 and 128.
   *
   * @param bitmask A bitmask
   * @return InetAddress object with an IP address based on the bitmask
   */
  protected static InetAddress createIPv6FromBitmask(int bitmask) {
    if (AddressUtil.isValidIPv6CIDR(bitmask)) {
      throw new IllegalArgumentException("Invalid IPv6 bitmask. Has to be between 0 and 128");
    }

    InetAddress address = null;
    try {
      address = setBitsFromLeftToRight(IPV6_BYTE_COUNT, bitmask);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

    return address;
  }

  private static InetAddress setBitsFromLeftToRight(int sizeOfAddressArray, int bitmask)
      throws UnknownHostException {
    byte[] octets = new byte[sizeOfAddressArray];
    int amountOfFullySetOctets = bitmask / 8;
    int partiallySetBitsInLastOctet = bitmask % 8;

    for (int i = 0; i < amountOfFullySetOctets; i++) {
      octets[i] = setByte(8);
    }

    if (partiallySetBitsInLastOctet != 0) {
      octets[amountOfFullySetOctets] = setByte(partiallySetBitsInLastOctet);
    }

    return InetAddress.getByAddress(octets);
  }

  private static byte setByte(int setBits) {
    int newByte = 0xFF;

    newByte = newByte >> setBits;
    newByte = ~newByte;
    newByte = newByte & 0xFF;

    return (byte) newByte;
  }
}
