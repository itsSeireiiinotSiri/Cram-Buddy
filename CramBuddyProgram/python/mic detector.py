import pyaudio

pa = pyaudio.PyAudio()
info = pa.get_host_api_info_by_index(0)
num_devices = info.get('deviceCount')

for i in range(num_devices):
    device_info = pa.get_device_info_by_host_api_device_index(0, i)
    print(f"Device {i}: {device_info['name']}")
