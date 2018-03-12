/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.jetbrains.annotations.NotNull;

/**
 * Masquerading logic for both IPv4 and IPv6.
 *
 */
public abstract class Bauta {

  private InetAddress maskingAddress;

  /**
   * Bauta object with IP bitmask.
   *
   * @param bitmask IPv4 or IPv6 bitmask for masquerading
   */
  protected Bauta(@NotNull final InetAddress bitmask) {

    maskingAddress = bitmask;
  }

  /**
   * Masquerade any IP address with the bitmask set in the constructor of this object.
   *
   * @param addressToMask IP address to mask
   * @return Masqueraded IP address
   * @throws UnknownHostException If {@code addressToMask} is not a valid IP address
   */
  protected InetAddress maskAnyIPAddress(@NotNull final InetAddress addressToMask)
      throws UnknownHostException {

    return masqueradeIPAddress(addressToMask);
  }

  /**
   * Masquerade only public routable IP addresses with the bitmask set in the constructor of this object.
   *
   * @param addressToMask IP address to mask
   * @return Masqueraded IP address
   * @throws UnknownHostException If {@code addressToMask} is not a valid IP address
   */
  protected InetAddress maskPublicRoutableIPAddressOnly(@NotNull final InetAddress addressToMask)
      throws UnknownHostException {

    final boolean isNotPublicRoutable =
        addressToMask.isAnyLocalAddress() ||
            addressToMask.isLoopbackAddress() ||
            addressToMask.isLinkLocalAddress() ||
            addressToMask.isSiteLocalAddress();

    return isNotPublicRoutable ? addressToMask : masqueradeIPAddress(addressToMask);
  }

  private InetAddress masqueradeIPAddress(@NotNull final InetAddress addressToMask)
      throws UnknownHostException {

    int length = getLengthOfAddress(addressToMask);

    byte[] inputAddress = addressToMask.getAddress();
    byte[] mask = maskingAddress.getAddress();
    byte[] outputAddress = new byte[length];

    for (int i = 0; i < length; i++) {
      outputAddress[i] = (byte) (inputAddress[i] & mask[i]);
    }

    return InetAddress.getByAddress(outputAddress);
  }

  private int getLengthOfAddress(@NotNull final InetAddress address) {
    return address.getAddress().length;
  }

}
