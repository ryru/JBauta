/*
 * Copyright 2018 Pascal K. (ryru@addere.ch)
 * Released under the terms of the 2-Clause BSD License.
 */

package ch.addere.jbauta;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Masquerading logic for both IPv4 and IPv6.
 *
 * @author Pascal K. (ryru@addere.ch)
 * @version $Id$
 */
public abstract class Bauta {

  private InetAddress maskingAddress;

  /**
   * Bauta object with IP bitmask.
   *
   * @param bitmask IPv4 or IPv6 bitmask for masquerading
   */
  protected Bauta(final InetAddress bitmask) {
    if (bitmask == null) {
      throw new IllegalArgumentException("NULL reference: bitmask");
    }

    maskingAddress = bitmask;
  }

  /**
   * Masquerade any IP address with the bitmask set in the constructor of this object.
   *
   * @param addressToMask IP address to mask
   * @return Masqueraded IP address
   * @throws UnknownHostException If {@code addressToMask} is not a valid IP address
   */
  protected InetAddress maskAnyIPAddress(final InetAddress addressToMask)
      throws UnknownHostException {
    if (addressToMask == null) {
      throw new IllegalArgumentException("NULL reference: addressToMask");
    }

    return masqueradeIPAddress(addressToMask);
  }

  /**
   * Masquerade only public routable IP addresses with the bitmask set in the constructor of this object.
   *
   * @param addressToMask IP address to mask
   * @return Masqueraded IP address
   * @throws UnknownHostException If {@code addressToMask} is not a valid IP address
   */
  protected InetAddress maskPublicRoutableIPAddressOnly(final InetAddress addressToMask)
      throws UnknownHostException {
    if (addressToMask == null) {
      throw new IllegalArgumentException("NULL reference: addressToMask");
    }

    final boolean isNotPublicRoutable =
        addressToMask.isAnyLocalAddress() ||
            addressToMask.isLoopbackAddress() ||
            addressToMask.isLinkLocalAddress() ||
            addressToMask.isSiteLocalAddress();

    return isNotPublicRoutable ? addressToMask : masqueradeIPAddress(addressToMask);
  }

  private InetAddress masqueradeIPAddress(final InetAddress addressToMask)
      throws UnknownHostException {
    if (addressToMask == null) {
      throw new IllegalArgumentException("NULL reference: addressToMask");
    }

    int length = getLengthOfAddress(addressToMask);

    byte[] inputAddress = addressToMask.getAddress();
    byte[] mask = maskingAddress.getAddress();
    byte[] outputAddress = new byte[length];

    for (int i = 0; i < length; i++) {
      outputAddress[i] = (byte) (inputAddress[i] & mask[i]);
    }

    return InetAddress.getByAddress(outputAddress);
  }

  private int getLengthOfAddress(InetAddress address) {
    return address.getAddress().length;
  }

}
