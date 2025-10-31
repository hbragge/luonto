#!/usr/bin/env bash
# crossfade-all.sh
# Usage: ./crossfade-all.sh output.mp3 [fade_seconds]
# Joins all *.wav files in the current directory with crossfades and exports MP3.

set -euo pipefail
shopt -s nullglob

out="${1:-mix.mp3}"
fade_dur="${2:-10}"

# Collect and sort WAVs (handles spaces via null-terminators)
mapfile -d '' inputs < <(printf '%s\0' *.wav | sort -z)

if (( ${#inputs[@]} == 0 )); then
  echo "No .wav files found in the current directory." >&2
  exit 1
fi

# Build ffmpeg -i arguments safely
ffinputs=()
for f in "${inputs[@]}"; do
  echo $f
  ffinputs+=(-i "$f")
done

# If only one file: just encode to mp3 (no crossfade possible)
if (( ${#inputs[@]} == 1 )); then
  ffmpeg "${ffinputs[@]}" -vn -c:a libmp3lame -b:a 192k "$out"
  echo "Done: $out (single file, no crossfades)"
  exit 0
fi

# Build filter_complex with proper quoting
filter="[0:a][1:a]acrossfade=d=${fade_dur}:c1=tri:c2=tri[a0]"

for ((k=2; k<${#inputs[@]}; k++)); do
  prev_label="a$((k-2))"
  new_label="a$((k-1))"
  filter="${filter};[${prev_label}][${k}:a]acrossfade=d=${fade_dur}:c1=tri:c2=tri[${new_label}]"
done

# Final label = a(N-2)
final_label="a$(( ${#inputs[@]} - 2 ))"

# Run ffmpeg with quoted filter
ffmpeg "${ffinputs[@]}" \
  -filter_complex "$filter" -map "[${final_label}]" \
  -vn -c:a libmp3lame -b:a 192k "$out"

echo "✅ Done: $out"
